package com.example.demo.src.review;

import com.example.demo.src.review.dto.GetStoreReviewRes;
import com.example.demo.src.review.dto.PostReviewReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ReviewDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public List<GetStoreReviewRes> getReviewList(int s_id){

        String getReviewListQuery = "select content, score from review where store_id = ?";
        int queryParam = s_id;

        return this.jdbcTemplate.query(getReviewListQuery,
                (rs, rowNum) -> new GetStoreReviewRes(
                        rs.getInt("id"),
                        rs.getString("content"),
                        rs.getInt("score")
                ), queryParam);


    }

    public int createReview(int u_id, PostReviewReq postReviewReq) {
        String createReviewQuery = "insert into review(user_id,store_id,content,score) values(?,?,?,?)";
        Object[] createReviewParam = new Object[]{u_id,postReviewReq.getStore_id(),postReviewReq.getContent(),postReviewReq.getScore()};
        return this.jdbcTemplate.update(createReviewQuery,createReviewParam);

    }

    public void delReview(int reviewIdx) {
        String delReviewQuery = "DELETE FROM review WHERE id =?";

        this.jdbcTemplate.update(delReviewQuery, reviewIdx);

    }
}
