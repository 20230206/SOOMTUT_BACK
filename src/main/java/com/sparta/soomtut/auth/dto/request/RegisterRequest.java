package com.sparta.soomtut.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//lombok
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String nickname;
    private String email;
    private String password;

    private String provider;
    private String providerId;

    private String address;
    private float posX;
    private float posY;
    private String sido;
    private String sigungu;
    private String bname;

}
