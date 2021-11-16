package com.example.demo.src.zzim.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetManyZzimStoreRes {

    int storeIdx;
    String name;
    int zzimCount;
}
