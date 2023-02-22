package com.sparta.soomtut.util.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenType {
    OAUTH2("OAUTH2", ExpireTime.OAUTH2_TOKEN_TIME),
    REFRESH("REFRESH", ExpireTime.REFRESH_TOKEN_TIME),
    ACCESS("ACCESS", ExpireTime.ACCESS_TOKEN_TIME);

    public final String type;
    public final Long expireTime;

    class ExpireTime {
        private static final long REFRESH_TOKEN_TIME = 14 * 24 * 60 * 60 * 1000L; // 14일
        private static final long ACCESS_TOKEN_TIME = 30 * 60 * 1000; // 30분
        private static final long OAUTH2_TOKEN_TIME = 60 * 1000L; // 1분
    }
}