package com.sparta.soomtut.util.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    // auth responses
    LOGIN_OK("로그인에 성공하였습니다", 200),
    MESSGE_OK("메세지가 전달 되었습니다", 200),
    REGISTER_CHECK_OK("회원가입-유효성 검사에 성공하였습니다", 200),
    LOGOUT_OK("로그아웃에 성공하였습니다", 200),
    TOKEN_CHECK_OK("토큰 검증에 성공하였습니다", 200),
    REFRESH_OK("리프레쉬 토큰 발급에 성공하였습니다", 200),
    OAUTH_LOGIN_OK("소셜 로그인에 성공하였습니다", 200),
    
    MEMBER_MYINFO_OK("내 정보 조회에 성공하였습니다", 200);

    private final String message;
    private final int status;
}