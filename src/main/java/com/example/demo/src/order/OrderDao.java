package com.example.demo.src.order;

import com.example.demo.src.order.dto.GetOrderListRes;
import com.example.demo.src.order.dto.PostOrderFoodReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class OrderDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public List<GetOrderListRes> getOrder(int userIdx) {
        String getOrderQuery = "select food_id, order_price, `count` from ((select ID from `order` where user_id = ?) AS a JOIN `order_food` AS b ON a.ID = b.order_id)";
        int  getOrderQueryParam = userIdx;
        return this.jdbcTemplate.query(getOrderQuery,
                (rs, rowNum) -> new GetOrderListRes(
                        rs.getInt("food_id"),
                        rs.getInt("order_price"),
                        rs.getInt("count")
                ),getOrderQueryParam);

    }

    public int createOrder(PostOrderFoodReq postOrderFoodReq, int userIdx) {
        String createOrderQuery = "insert into `order`(user_id) values(?)";
        this.jdbcTemplate.update(createOrderQuery,userIdx);

        String lastInserIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값은 가져온다.
        int lastOrderIdx =  this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);


        int cost = this.jdbcTemplate.queryForObject("select cost from food where id = ?",int.class,postOrderFoodReq.getFoodIdx());
        int orderPrice = postOrderFoodReq.getCount() * cost;

        String createOrderFoodQuery = "insert into order_food(food_id,order_id,order_price,count) values(?,?,?,?)";
        Object[] param = new Object[]{postOrderFoodReq.getFoodIdx(),lastOrderIdx,orderPrice,postOrderFoodReq.getCount()};
        this.jdbcTemplate.update(createOrderFoodQuery,param);
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);



    }

    public void deleteOrder(int orderIdx) {

        String delOrderFoodQuery = "DELETE FROM `order_food` WHERE `order_id` = ?";
        String delOrderQuery = "DELETE FROM `order` WHERE id = ?";

        this.jdbcTemplate.update(delOrderFoodQuery,orderIdx);
        this.jdbcTemplate.update(delOrderQuery,orderIdx);


    }
}
