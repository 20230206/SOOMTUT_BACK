package com.sparta.soomtut.auth.service;

import com.sparta.soomtut.auth.dto.LoginRequest;
import com.sparta.soomtut.auth.dto.OAuthLocationRequest;
import com.sparta.soomtut.auth.dto.OAuthLoginRequest;
import com.sparta.soomtut.auth.entity.Auth;
import com.sparta.soomtut.dto.request.RegisterRequest;
import com.sparta.soomtut.dto.response.LoginResponse;
import com.sparta.soomtut.dto.response.MemberInfoResponse;

public interface AuthService {
    MemberInfoResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    LoginResponse oauthLogin(OAuthLoginRequest request);

    String createAccessToken(String refresh);
    void saveAuth(Auth auth);
    MemberInfoResponse setOAuthLocation(OAuthLocationRequest request, String refresh);

}
