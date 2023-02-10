package com.sparta.soomtut.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.soomtut.dto.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AuthController {

    @PostMapping(value = "/signin")
    public void SignIn(
            /*SignIn Request*/
    )
    {
        // Service

        // return
    }

    @PostMapping(value = "/signinkakao")
    public void SignInForKakao(
            /*SignIn Request*/
    )
    {
        // Service

        // return
    }

    @PostMapping(value = "/signup")
    public String SignUp(
            /*SignUp Request*/
            @RequestBody SignupRequestDto requestDto
    )
    {
        // Service
        System.out.println(requestDto.getUsername() + requestDto.getEmail() + requestDto.getPassword());
        // return
        return "Method: [Sign Up] called";
    }

    @PostMapping(value = "/signupkakao")
    public void SignUpKakao(
            /*SignUp Request*/
    )
    {
        // Service

        // return
    }

    @PostMapping(value = "signout")
    public void SignOut(

    )
    {

    }
}
