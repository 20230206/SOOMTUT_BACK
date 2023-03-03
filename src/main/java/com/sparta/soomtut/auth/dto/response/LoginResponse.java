package com.sparta.soomtut.auth.dto.response;

import com.sparta.soomtut.member.entity.enums.MemberState;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//lombok
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class LoginResponse {
    private MemberState state;
    private String token;

}