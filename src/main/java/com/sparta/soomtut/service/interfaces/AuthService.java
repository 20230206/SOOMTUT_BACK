package com.sparta.soomtut.service.interfaces;

import com.sparta.soomtut.entity.Auth;

import com.sparta.soomtut.dto.request.LoginRequest;
import com.sparta.soomtut.dto.request.RegisterRequest;
import com.sparta.soomtut.dto.request.OAuthLoginRequest;

import com.sparta.soomtut.dto.response.LoginResponse;
import com.sparta.soomtut.dto.response.MemberInfoResponse;

public interface AuthService {
    MemberInfoResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    LoginResponse oauthLogin(OAuthLoginRequest request);

    String createAccessToken(String refresh);
    void saveAuth(Auth auth);

}
