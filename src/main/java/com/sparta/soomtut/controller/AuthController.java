package com.sparta.soomtut.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.soomtut.dto.request.SigninRequestDto;
import com.sparta.soomtut.dto.request.SignupRequestDto;
import com.sparta.soomtut.dto.response.SigninResponseDto;
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

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("msg", message);
        return ResponseEntity.ok().header("Authorization", response.getToken()).body(dataMap);
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<?> signup(
        @RequestBody SignupRequestDto requestDto
    )
    {
        authService.signup(requestDto);
        var data = "Method[signUp] has called by front";
        return ResponseEntity.ok().body(data);
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
    public void SignOut(

    )
    {

    }

    @GetMapping(value = "/validtoken")
    public ResponseEntity<?> checkToken(HttpServletRequest request) {
        var data = authService.checkToken(request);
        return ResponseEntity.ok().body(data);
    }
    
}
