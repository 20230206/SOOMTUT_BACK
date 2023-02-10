package com.sparta.soomtut.dto;

import com.sparta.soomtut.enums.MemberRole;

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
