package com.sparta.soomtut.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public void SignUp(
            /*SignUp Request*/
    )
    {
        // Service

        // return
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
