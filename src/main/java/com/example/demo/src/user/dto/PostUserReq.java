package com.example.demo.src.user.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostUserReq {
    private String email;
    private String password;
    private String name;
    private String zipcode;

}
