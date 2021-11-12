package com.example.demo.src.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetStoreInfoRes {

    private String name;
    private int cost;
    private int tip;
    private String desc;
}
