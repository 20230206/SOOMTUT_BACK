package com.sparta.soomtut.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OAuthLoginRequest {
    private String email;
    private String role;
    private int hash;
}
