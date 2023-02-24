package com.sparta.soomtut.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OAuthLocationRequest {
    private String address;
    private float vectorX;
    private float vectorY;
}
