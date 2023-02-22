package com.sparta.soomtut.util.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {
    LOGIN_OK("성공적으로 메세지가 전달 되었습니다", 200),
    MESSGE_OK("성공적으로 메세지가 전달 되었습니다", 200);

    private final String message;
    private final int status;
}