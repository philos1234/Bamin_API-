package com.example.demo.src.zzim;

import com.example.demo.src.zzim.dto.GetZzimStoreIdRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ZzimDao {

    private JdbcTemplate jdbcTemplate;
    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetZzimStoreIdRes> getStoreId(int u_idx) {

        String getStoreIdQuery = "select store_id from zzim where user_id = ?";
        int getStoreIdParam = u_idx;

        return this.jdbcTemplate.query(getStoreIdQuery,
                ((rs, rowNum) -> new GetZzimStoreIdRes(
                        rs.getInt("store_id")
                ))
                ,getStoreIdParam );


    }

    public int createZzim(int u_idx, int s_idx) {

        String createZzimQuery = "insert into zzim(user_id, store_id) values(?,?)";
        Object[] param = new Object[]{u_idx,s_idx};

        return this.jdbcTemplate.update(createZzimQuery,param);


    }
}
