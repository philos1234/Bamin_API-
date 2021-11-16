package com.example.demo.src.store;

import com.example.demo.src.store.dto.GetStoreInfoRes;
import com.example.demo.src.store.dto.GetStoreScoreRes;
import com.example.demo.src.user.dto.GetUserRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class StoreDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public GetStoreInfoRes getStore(int store_idx) {

        String getStoreQuery = "select id,`name`, min_cost, tip, `desc` from store where ID = ?";
        int getStoreParam = store_idx;

        return this.jdbcTemplate.queryForObject(getStoreQuery,
                (rs, rowNum) -> new GetStoreInfoRes(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("min_cost"),
                        rs.getInt("tip"),
                        rs.getString("desc")
                )
                ,store_idx);

    }



    //페이징 처리
    public List<GetStoreInfoRes> getStoreList(int last_idx){
        String getStoreListQuery = "select * from store LIMIT 3 OFFSET ?";
        int param = last_idx;

        return this.jdbcTemplate.query(getStoreListQuery,(rs, rowNum) ->
                    new GetStoreInfoRes(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("min_cost"),
                            rs.getInt("tip"),
                            rs.getString("desc")
                    )
                , param);



    }

    public GetStoreScoreRes getStoreScore(int storeIdx) {

        String getStoreScoreQuery = "SELECT R.store_id, name , AVG(score) AS sum_score FROM `review` AS R JOIN store AS S on R.store_id = S.ID  where R.store_id = ? GROUP BY store_id";
        int param =storeIdx;

        return this.jdbcTemplate.queryForObject(getStoreScoreQuery,
             (rs, rowNum) -> new GetStoreScoreRes(
                     rs.getString("name"),
                     rs.getInt("sum_score")
             ),param);


    }

    public List<GetStoreScoreRes> getStoreScoreList(){

        String getStoreScoreListQuery = "SELECT R.store_id, name , AVG(score) AS avg_score FROM `review` AS R JOIN store AS S on R.store_id = S.ID GROUP BY store_id";
        return this.jdbcTemplate.query(getStoreScoreListQuery,(rs, rowNum) -> new GetStoreScoreRes(
                rs.getString("name"),
                rs.getInt("avg_score")
        ));

    }
}
