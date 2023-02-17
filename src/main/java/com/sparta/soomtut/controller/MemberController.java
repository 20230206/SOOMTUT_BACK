package com.sparta.soomtut.controller;

import com.sparta.soomtut.dto.request.CreateReviewRequestDto;
import com.sparta.soomtut.dto.request.PageRequestDto;
import com.sparta.soomtut.entity.Review;
import com.sparta.soomtut.service.interfaces.MemberService;

import com.sparta.soomtut.util.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    private final MemberService memberService;


    @GetMapping(value = "/getmyinfo")
    ResponseEntity<?> getMyInfo(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    )
    {
        var data = memberService.getMemberInfo(userDetails.getMember());
        return ResponseEntity.ok().body(data);
    }


    @GetMapping(value = "/member/mypage/nickname")
    public ResponseEntity<?> getNickname(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        String nickname = memberService.getNickname(userDetails.getMember());
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

    //리뷰 조회
    @GetMapping(value = "/review")
    public Page<Review> getReview(@ModelAttribute PageRequestDto pageRequest, @AuthenticationPrincipal UserDetailsImpl userDetails){

        return memberService.getReview(pageRequest,userDetails.getMember());
    }

    //리뷰 삭제
    @PostMapping(value = "/review/{reviewId}")
    public ResponseEntity<?> deleteReviewRequest(
            @PathVariable Long reviewId

    ){
        String msg = memberService.deleteReviewRequest(reviewId);
        return ResponseEntity.ok().body(msg);
    }

}
