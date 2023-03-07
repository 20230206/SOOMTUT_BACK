package com.sparta.soomtut.auth.service;

import com.sparta.soomtut.auth.dto.request.LoginRequest;
import com.sparta.soomtut.auth.dto.request.OAuthInitRequest;
import com.sparta.soomtut.auth.dto.request.OAuthLoginRequest;
import com.sparta.soomtut.auth.dto.request.RegisterRequest;
import com.sparta.soomtut.auth.dto.response.LoginResponse;
import com.sparta.soomtut.auth.entity.Auth;
import com.sparta.soomtut.member.dto.response.MemberResponse;

public interface AuthService {

    MemberResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    LoginResponse oauthLogin(OAuthLoginRequest request);
    String createAccessToken(String refresh);
    void saveAuth(Auth auth);
    MemberResponse updateOAuthInfo(OAuthInitRequest request, String refresh);

}
