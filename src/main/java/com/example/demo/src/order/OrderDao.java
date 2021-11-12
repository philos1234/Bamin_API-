package com.example.demo.src.order;

import com.example.demo.src.order.dto.GetOrderListRes;
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
}
