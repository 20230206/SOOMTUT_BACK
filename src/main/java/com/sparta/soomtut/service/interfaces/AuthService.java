package com.sparta.soomtut.service.interfaces;

import com.sparta.soomtut.dto.request.SigninRequestDto;
import com.sparta.soomtut.dto.request.SignupRequestDto;
import com.sparta.soomtut.dto.response.SigninResponseDto;

public interface AuthService {

    void signup(SignupRequestDto requestDto);

    SigninResponseDto signin(SigninRequestDto requestDto);

}
