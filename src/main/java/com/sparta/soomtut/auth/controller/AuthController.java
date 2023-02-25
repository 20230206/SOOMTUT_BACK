package com.sparta.soomtut.auth.controller;


import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.soomtut.auth.dto.request.LoginRequest;
import com.sparta.soomtut.auth.dto.request.OAuthInfoRequest;
import com.sparta.soomtut.auth.dto.request.OAuthLoginRequest;
import com.sparta.soomtut.auth.dto.request.RegisterRequest;
import com.sparta.soomtut.auth.service.AuthService;
import com.sparta.soomtut.member.service.MemberService;
import com.sparta.soomtut.util.cookies.RefreshCookie;
import com.sparta.soomtut.util.response.SuccessCode;
import com.sparta.soomtut.util.response.ToResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")

public class AuthController {
    private final MemberService memberService;
    private final AuthService authService;

    private static final String REFRESH_KEY = "refresh";

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(
        @RequestBody LoginRequest requestDto
    )
    {
        var data = authService.login(requestDto);
        var cookie = RefreshCookie.getCookie(data.getToken(), true);

        return ToResponse.of(data, cookie, SuccessCode.LOGIN_OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(
        @RequestBody RegisterRequest requestDto
    )
    {
        var data = authService.register(requestDto);
        return ToResponse.of(data, SuccessCode.LOGIN_OK);
    }

    @GetMapping(value = "/register/check")
    public ResponseEntity<?> checkduple (
        @RequestParam(required = false, value = "email") String email, 
        @RequestParam(required = false, value = "nickname") String nickname
    ) {
        boolean data = false;
        if(nickname == null && email != null) { data = memberService.existsMemberByEmail(email); }
        if(email == null && nickname != null) { data = memberService.existsMemberByNickname(nickname); }

        return ToResponse.of(data, SuccessCode.REGISTER_CHECK_OK);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<?> logout(
        @CookieValue(name = REFRESH_KEY, required=false) String refresh
    )
    {   
        // TODO: refresh token을 black list 처리 할지 아직 정해지지 않음
        // redis 서버 이용하거나
        // 시큐리티 내장된 token session

        var cookie = RefreshCookie.getCookie("", false);
        boolean data = false;
        return ToResponse.of(data, cookie, SuccessCode.LOGOUT_OK);
    }

    @GetMapping(value = "/get-accesstoken")
    public ResponseEntity<?> getAccessToken(
        @CookieValue(name = REFRESH_KEY, required=false) String refresh) 
    {
        if(refresh == null) return ResponseEntity.ok().body(false);

        String accesstoken = authService.createAccessToken(refresh);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, accesstoken);

        return ToResponse.of(true, headers, SuccessCode.TOKEN_CHECK_OK);
    }
    
    @PostMapping(value="/oauth-login") 
    public ResponseEntity<?> oauthLogin(@RequestBody OAuthLoginRequest request) {
        System.out.print(request);
        var token = authService.oauthLogin(request);
        var cookie = RefreshCookie.getCookie(token.getToken(), true);

        return ToResponse.of(true, cookie, SuccessCode.REFRESH_OK);
    }

    @PutMapping(value="/oauth-updateinfo")
    public ResponseEntity<?> updateOAuthInfo(
        @RequestBody OAuthInfoRequest request,
        @CookieValue(REFRESH_KEY) String refresh
    ) 
    {
        var data = authService.updateOAuthInfo(request, refresh);
        return ToResponse.of(data, SuccessCode.OAUTH_LOGIN_OK);
    }
}
