package com.example.demo.src.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetUserRes {
    private int userIdx;
    private String name;
    private String email;
    private String password;
    private String zipcode;
}
