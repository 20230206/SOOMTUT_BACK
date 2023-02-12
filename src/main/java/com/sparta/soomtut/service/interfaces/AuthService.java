package com.sparta.soomtut.service.interfaces;

import com.sparta.soomtut.dto.SigninRequestDto;
import com.sparta.soomtut.dto.SigninResponseDto;
import com.sparta.soomtut.dto.SignupRequestDto;

public interface AuthService {

    void signup(SignupRequestDto requestDto);

    SigninResponseDto signin(SigninRequestDto requestDto);

}
