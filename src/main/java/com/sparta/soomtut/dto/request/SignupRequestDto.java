package com.sparta.soomtut.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//lombok
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class SignupRequestDto {
    private String nickname;
    private String email;
    private String password;
}
