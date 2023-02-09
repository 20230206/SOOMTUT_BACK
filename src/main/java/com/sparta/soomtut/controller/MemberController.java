package com.sparta.soomtut.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController  {
    /*
     MemberServiceImpl
     ReviewServiceImpl
     */

    @GetMapping(value = "/member/{memberId}/mypage/nickname")
    public String getNickname(
            @RequestParam Long memberId
    ) {
        // Service

        // return

        return "";
    }

    @PutMapping(value = "/member/{memberId}/mypage/nickname")
    public String setNickname(
        @RequestParam Long memberId
        /* RequestBody */
    ) {
        // Service

        // return
        return "";
    }


    @PutMapping(value = "/member/{memberId}/mypage/password")
    public String setPassword(
            @RequestParam Long memberId
            /* RequestBody */
    ) {
        // Service

        // return
        return "";
    }

    @PutMapping(value = "/member/{memberId}/mypage/email")
    public String setEmail(
            @RequestParam Long memberId
            /* RequestBody */
    ) {
        // Service

        // return
        return "";
    }

    @GetMapping(value = "/member/{memberId}/mypage/location")
    public String getLocation(
            @RequestParam Long memberId
    ) {
        // Service

        // return

        return "";
    }

    @PutMapping(value = "/member/{memberId}/mypage/location")
    public String setLocation(
            @RequestParam Long memberId
            /* RequestBody */
    ) {
        // Service

        // return
        return "";
    }

    @GetMapping(value = "/member/{memberId}/mypage/signupdate")
    public String getSignupDate(
            @RequestParam Long memberId
    ) {
        // Service

        // return

        return "";
    }

    @GetMapping(value = "/member/{memberId}/mypage/level")
    public String getLevel(
            @RequestParam Long memberId
    ) {
        // Service

        // return

        return "";
    }

    @GetMapping(value = "/member/{memberId}/mypage/image")
    public String getImage(
            @RequestParam Long memberId
    ) {
        // Service

        // return

        return "";
    }

    @GetMapping(value = "/member/{memberId}/mypage/star")
    public String getStar(
            @RequestParam Long memberId
    ) {
        // Service

        // return

        return "";
    }

    @DeleteMapping(value = "/member/{memberId}/mypage/delete")
    public String deleteAccount(
            @RequestParam Long memberId
    ) {
        // Service

        // return

        return "";
    }


    // 수업 완료 EndPoint 추가


    // 리뷰 생성
    @GetMapping(value = "/board/{postId}")
    public String createReview(
            @RequestParam Long postId
            /* UserDetails */
    ) {
        // Service

        // return

        return "";
    }

}