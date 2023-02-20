package com.sparta.soomtut.controller;


import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.soomtut.dto.request.SigninRequestDto;
import com.sparta.soomtut.dto.request.SignupRequestDto;
import com.sparta.soomtut.dto.response.SigninResponseDto;
import com.sparta.soomtut.service.interfaces.AuthService;
import com.sparta.soomtut.service.interfaces.MemberService;
import com.sparta.soomtut.util.cookies.RefreshCookie;
import com.sparta.soomtut.util.response.ErrorCode;
import com.sparta.soomtut.util.response.SuccessCode;
import com.sparta.soomtut.util.response.ToResponse;

import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")

public class AuthController {
    private final MemberService memberService;
    private final AuthService authService;

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(
        @RequestBody SigninRequestDto requestDto
    )
    {
        SigninResponseDto response = authService.login(requestDto);
        var message = "Method[signin] has called by front";

        ResponseCookie cookie = RefreshCookie.getCookie(response.getToken(), true);

        return ToResponse.of(null, cookie, SuccessCode.LOGIN_OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(
        @RequestBody SignupRequestDto requestDto
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

        return ResponseEntity.ok().body(data);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<?> signOut(
        @CookieValue(name = "refresh", required=false) String refresh
    )
    {   
        // TODO: refresh token을 black list 처리 할지 아직 정해지지 않음
        // redis 서버 이용하거나
        // 시큐리티 내장된 token session

        // cookie의 정보 삭제
        ResponseCookie cookie = RefreshCookie.getCookie("", false);
                                    
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(false);
    }

    @GetMapping(value = "/validtoken")
    public ResponseEntity<?> checkToken(
        @CookieValue(name = "refresh", required=false) String refresh) 
    {
        if(refresh == null) return ResponseEntity.ok().body(false);

        // Refresh Token이 유효한지 판단한다.
        boolean isvalid = authService.checkToken(refresh);

        // refresh token이 valid가 되면, access token을 생성해주는 단계로 넘어간다.
        // 그리고 access token을 발급해 front로 전달해준다.
        if(isvalid) {
            String accesstoken = authService.createToken(refresh);

            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, accesstoken).body(isvalid);
        }
        else {
            return ResponseEntity.badRequest().body(ErrorCode.INVALID_TOKEN);
        }
    }
    
    @GetMapping(value="/createrefreshforoauth2") 
    public ResponseEntity<?> createRefresh(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String response = authService.createToken(token);
        
        ResponseCookie cookie = RefreshCookie.getCookie(response, true);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(true);
    }
}
