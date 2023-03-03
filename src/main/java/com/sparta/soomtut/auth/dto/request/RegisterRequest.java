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
    private String address;
    private float vectorX;
    private float vectorY;

}
