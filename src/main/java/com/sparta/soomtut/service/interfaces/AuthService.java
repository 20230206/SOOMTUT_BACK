package com.sparta.soomtut.service.interfaces;

import com.sparta.soomtut.dto.request.SigninRequestDto;
import com.sparta.soomtut.dto.request.SignupRequestDto;
import com.sparta.soomtut.dto.response.SigninResponseDto;
import com.sparta.soomtut.dto.response.MemberInfoResponseDto;

public interface AuthService {
    MemberInfoResponseDto register(SignupRequestDto requestDto);
    SigninResponseDto login(SigninRequestDto requestDto);

    boolean checkToken(String token);
    String createToken(String token);

}
