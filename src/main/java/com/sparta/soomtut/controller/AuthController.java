package com.sparta.soomtut.controller;


import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.soomtut.dto.request.LoginRequest;
import com.sparta.soomtut.dto.request.RegisterRequest;
import com.sparta.soomtut.dto.request.OAuthLoginRequest;
import com.sparta.soomtut.service.interfaces.AuthService;
import com.sparta.soomtut.service.interfaces.MemberService;
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
        @CookieValue(name = "refresh", required=false) String refresh
    )
    {   
        // TODO: refresh token을 black list 처리 할지 아직 정해지지 않음
        // redis 서버 이용하거나
        // 시큐리티 내장된 token session

        // cookie의 정보 삭제
        var cookie = RefreshCookie.getCookie("", false);
        boolean data = false;
        return ToResponse.of(data, cookie, SuccessCode.LOGOUT_OK);
    }

    @GetMapping(value = "/validtoken")
    public ResponseEntity<?> checkToken(
        @CookieValue(name = "refresh", required=false) String refresh) 
    {
        if(refresh == null) return ResponseEntity.ok().body(false);

        String accesstoken = authService.createAccessToken(refresh);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, accesstoken);

        return ToResponse.of(true, headers, SuccessCode.TOKEN_CHECK_OK);
    }
    
    @GetMapping(value="/oauthlogin") 
    public ResponseEntity<?> oauthLogin(@ModelAttribute OAuthLoginRequest request) {
        System.out.print(request);
        var data = authService.oauthLogin(request);

        return ToResponse.of(true, SuccessCode.REFRESH_OK);
    }
}
