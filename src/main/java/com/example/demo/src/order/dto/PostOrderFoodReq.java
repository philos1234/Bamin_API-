package com.example.demo.src.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostOrderFoodReq {

    int storeIdx;
    int foodIdx;
    int count; //수량
}
