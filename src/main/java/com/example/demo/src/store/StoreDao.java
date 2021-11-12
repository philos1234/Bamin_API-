package com.example.demo.src.store;

import com.example.demo.src.store.dto.GetStoreInfoRes;
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

        String getStoreQuery = "select `name`, min_cost, tip, `desc` from store where ID = ?";
        int getStoreParam = store_idx;

        return this.jdbcTemplate.queryForObject(getStoreQuery,
                (rs, rowNum) -> new GetStoreInfoRes(
                        rs.getString("name"),
                        rs.getInt("min_cost"),
                        rs.getInt("tip"),
                        rs.getString("desc")
                )
                ,store_idx);

    }
}
