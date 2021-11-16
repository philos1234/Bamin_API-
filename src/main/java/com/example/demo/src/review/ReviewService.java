package com.example.demo.src.review;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.review.dto.GetStoreReviewRes;
import com.example.demo.src.review.dto.PostReviewReq;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.POST_REVIEW_CREATE_FAIL;

@Service
public class ReviewService {

    private ReviewDao reviewDao;

    public ReviewService(ReviewDao reviewDao){
        this.reviewDao = reviewDao;

    }

    public List<GetStoreReviewRes> getReview(int s_id) throws BaseException {
        try{
            List<GetStoreReviewRes> ret = reviewDao.getReviewList(s_id);
            return ret;

        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void createReview(int u_id, PostReviewReq postReviewReq) throws BaseException{
        try{
            int ret = reviewDao.createReview(u_id,postReviewReq);
            if(ret ==0)throw new BaseException(POST_REVIEW_CREATE_FAIL);

        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void delReview(int reviewIdx) throws BaseException{
        try{

            reviewDao.delReview(reviewIdx);


        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }

    }
}
