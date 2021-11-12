package com.example.demo.src.user.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class PatchUserReq {
    private int userIdx;
    private String name;
    private String email;
    private String zipcode;

    public PatchUserReq(int userIdx ,User user){
        this.userIdx = userIdx;
        this.name = user.getName();
        this.email = user.getEmail();
        this.zipcode = user.getZipcode();

    }

}
