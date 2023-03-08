package com.sparta.soomtut.util.cookies;

import java.time.Duration;

import org.springframework.http.ResponseCookie;

public class RefreshCookie {
    public static final String REFRESH_KEY = "refresh";
    public static ResponseCookie getCookie(String token, boolean create) {
        Long age = 0L;
        if(create) age = 14L;
        else age = 0L;

        ResponseCookie cookie = ResponseCookie.from(REFRESH_KEY, token)
        .httpOnly(true)
        .maxAge(Duration.ofDays(age))
        .path("/")
        // .sameSite("None") // https 적용 시 활성화 할것
        .build();
        return cookie;
    }

}
