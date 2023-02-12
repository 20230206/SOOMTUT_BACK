package com.sparta.soomtut.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//lombok
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SigninRequestDto {
    private String email;
    private String password;
}