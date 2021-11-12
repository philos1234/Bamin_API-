package com.example.demo.src.review;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.review.dto.GetStoreReviewRes;
import com.example.demo.src.review.dto.PostReviewReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }


    @GetMapping("")
    public BaseResponse<List<GetStoreReviewRes>>getReview(@RequestParam(name="storeId") int s_id){

        try{
            List<GetStoreReviewRes> ret = reviewService.getReview(s_id);
            return new BaseResponse<>(ret);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @PostMapping("/{userIdx}")
    public BaseResponse<String> creatReview(@PathVariable(name="userIdx") int userIdx, @RequestBody PostReviewReq postReviewReq) throws BaseException {
        try {
           reviewService.createReview(userIdx,postReviewReq);

           return new BaseResponse<>("리뷰 생성 성공");


        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());

        }

    }


}
