package com.sparta.soomtut.review.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.soomtut.util.response.SuccessCode;
import com.sparta.soomtut.util.response.ToResponse;
import com.sparta.soomtut.util.dto.request.PageRequestDto;
import com.sparta.soomtut.member.service.MemberService;
import com.sparta.soomtut.util.security.UserDetailsImpl;

import com.sparta.soomtut.review.dto.request.CreateReviewRequestDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
        
    private final MemberService memberService;

    // 리뷰 생성 - 리뷰 삭제랑 겹쳐서 임시로 create 넣어둠.
    @PostMapping(value = "/create/{lectureid}")
    public ResponseEntity<?> createReview(
            @PathVariable Long lectureid,
            @RequestBody CreateReviewRequestDto reviewRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            /* UserDetails */
    ) {
        // Service
        String msg = memberService.createReview(lectureid,reviewRequestDto,userDetails.getMember());
        // return

        return ToResponse.of(msg, SuccessCode.REVIEW_CREATE_OK);
    }

    //리뷰 조회
    @GetMapping(value = "/{reviewid}")
    public ResponseEntity<?> getReview(
        @ModelAttribute PageRequestDto pageRequest,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        var data = memberService.getReview(pageRequest,userDetails.getMember());
        return ToResponse.of(data, SuccessCode.REVIEW_GET_OK);
    }

    //리뷰 삭제
    @PostMapping(value = "/{reviewId}")
    public ResponseEntity<?> deleteReviewRequest(
            @PathVariable Long reviewId

    ) {
        String msg = memberService.deleteReviewRequest(reviewId);
        return ToResponse.of(msg, SuccessCode.REVIEW_DELETE_OK);
    }

    // 게시된 강의의 리뷰 조회
    @GetMapping(value = "/{lectureid}")
    public ResponseEntity<?> getReviewsByPost (
        @PathVariable Long lectureid
    ) {
        return ToResponse.of(null, SuccessCode.REVIEW_GETBYLECTURE_OK);
    }

    // 해당 멤버의 리뷰 조회
    @GetMapping(value = "/{memberid}") 
    public ResponseEntity<?> getReviewsByMember (
        @PathVariable Long memberid
    ) {
        return ToResponse.of(null, SuccessCode.REVIEW_GETBYMEMBER_OK);
    }
}






//    // 후기(작성/미작성) 수업 조회
//    @GetMapping(value = "/reviewFilter")
//    public Page<Post> getReviewFilter(
//            @ModelAttribute PageRequestDto pageRequest,
//            @AuthenticationPrincipal UserDetailsImpl userDetails
//    ){
//        return postService.getReviewFilter(pageRequest, userDetails.getMember());
//    }