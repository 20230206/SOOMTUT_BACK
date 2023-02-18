package com.sparta.soomtut.controller;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.soomtut.dto.request.SigninRequestDto;
import com.sparta.soomtut.dto.request.SignupRequestDto;
import com.sparta.soomtut.dto.response.SigninResponseDto;
import com.sparta.soomtut.exception.ErrorCode;
import com.sparta.soomtut.service.interfaces.AuthService;
import com.sparta.soomtut.service.interfaces.MemberService;

import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final MemberService memberService;
    private final AuthService authService;

    @PostMapping(value = "/signin")
    public ResponseEntity<?> signin(
        @RequestBody SigninRequestDto requestDto
    )
    {
        SigninResponseDto response = authService.signin(requestDto);
        var message = "Method[signin] has called by front";

        ResponseCookie cookie = ResponseCookie.from("refresh", response.getToken())
                                    .httpOnly(true)
                                    .maxAge(Duration.ofDays(14))
                                    .path("/")
                                    .build();

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("msg", message);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(dataMap);
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<?> signup(
        @RequestBody SignupRequestDto requestDto
    )
    {
        var data = authService.signup(requestDto);
        var message = "Method[signUp] has called by front";

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("data", data);
        dataMap.put("msg", message);

        return ResponseEntity.ok().body(dataMap);
    }

    @GetMapping(value = "/signup/check")
    public ResponseEntity<?> checkduple (
        @RequestParam(required = false, value = "email") String email,
        @RequestParam(required = false, value = "nickname") String nickname
    ) {
        boolean data = false;
        if(nickname == null && email != null) { data = memberService.existsMemberByEmail(email); }
        if(email == null && nickname != null) { data = memberService.existsMemberByNickname(nickname); }

        return ResponseEntity.ok().body(data);
    }

    @GetMapping(value = "/signup/check/nickname")
    public ResponseEntity<?> checkNickname (
        @RequestBody String requestDto
    ) {

        return ResponseEntity.ok().body(null);
    }

    @PostMapping(value = "/signout")
    public ResponseEntity<?> signOut(
        
    )
    {
        ResponseCookie cookie = ResponseCookie.from("refresh", "")
                                    .httpOnly(true)
                                    .maxAge(0)
                                    .path("/")
                                    .build();
                                    
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
        
        ResponseCookie cookie = ResponseCookie.from("refresh", response)
                                    .httpOnly(true)
                                    .maxAge(Duration.ofDays(14))
                                    .path("/")
                                    .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(null);
    }
}
