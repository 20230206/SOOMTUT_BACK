package com.sparta.soomtut.controller;

import com.sparta.soomtut.dto.CreateReviewRequestDto;
import com.sparta.soomtut.service.impl.MemberServiceImpl;
import com.sparta.soomtut.util.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class MemberController  {
    /*
     MemberServiceImpl
     ReviewServiceImpl
     */

    private final MemberServiceImpl memberService;



    @GetMapping(value = "/member/mypage/nickname")
    public ResponseEntity<?> getNickname(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        // Service
        String nickname = memberService.getNickname(userDetails.getMember());
        // return

        return ResponseEntity.status(HttpStatus.OK).body(nickname);
    }

    @PutMapping(value = "/member/mypage/nickname")
    public ResponseEntity<?> setNickname(
            @RequestBody String nickname,
            @AuthenticationPrincipal UserDetailsImpl userDetails
        /* RequestBody */
    ) {
        // Service
        String msg = memberService.updateNickname(nickname,userDetails.getMember());
        // return
        return ResponseEntity.status(HttpStatus.OK).body(msg) ;
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

    @GetMapping(value = "/member/mypage/location")
    public ResponseEntity<?> getLocation(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        // Service

        String location = memberService.getLocation(userDetails.getMember());
        // return

        return ResponseEntity.status(HttpStatus.OK).body(location);
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

    @GetMapping(value = "/member/mypage/signupdate")
    public ResponseEntity<?> getSignupDate(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        // Service
        LocalDate signupDate = memberService.getSignupDate(userDetails.getMember());
        // return

        return ResponseEntity.status(HttpStatus.OK).body(signupDate);
    }

    @GetMapping(value = "/member/mypage/level")
    public ResponseEntity<?> getLevel(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        // Service
        int level = memberService.getLevel(userDetails.getMember());
        // return

        return ResponseEntity.status(HttpStatus.OK).body(level);
    }

    @GetMapping(value = "/member/mypage/image")
    public ResponseEntity<?> getImage(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        // Service
        String image = memberService.getImage(userDetails.getMember());
        // return

        return ResponseEntity.status(HttpStatus.OK).body(image);
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
    @PostMapping(value = "/board/{postId}")
    public ResponseEntity<?> createReview(
            @RequestParam Long postId,
            @RequestBody CreateReviewRequestDto reviewRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            /* UserDetails */
    ) {
        // Service
        String msg = memberService.createReview(postId,reviewRequestDto,userDetails.getMember());
        // return

        return ResponseEntity.status(HttpStatus.CREATED).body(msg);
    }

}
