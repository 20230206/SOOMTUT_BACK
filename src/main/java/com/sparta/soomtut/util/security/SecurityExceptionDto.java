package com.sparta.soomtut.util.security;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SecurityExceptionDto {
    private int statusCode;
    private String msg;

    public SecurityExceptionDto(int statusCode, String msg) {
        this.statusCode = statusCode;
        this.msg = msg;
    }

    class ResponseMessage {
        public static final String FORBBIDEN = "접근이 거부됨";
        public static final String UNAUTHORIZED = "인증이 불가능함";
    }
}