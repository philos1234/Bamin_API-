package com.example.demo.src.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetOrderListRes {

    private int foodId; // 음식 ID
    private int orderPrice; //음식 가격
    private int count; //수량
}
