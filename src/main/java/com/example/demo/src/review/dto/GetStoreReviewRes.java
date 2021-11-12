package com.example.demo.src.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class GetStoreReviewRes {
    private String content;
    private int score;
}
