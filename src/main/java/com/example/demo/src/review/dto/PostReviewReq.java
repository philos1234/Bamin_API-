package com.example.demo.src.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostReviewReq {

    private String store_id;
    private String content;
    private int score;
}
