package com.sparta.soomtut.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAuthLoginRequest {
    private String email;
    private String role;
}
