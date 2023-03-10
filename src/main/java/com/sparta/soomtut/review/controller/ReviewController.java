package com.sparta.soomtut.review.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.soomtut.util.response.SuccessCode;
import com.sparta.soomtut.util.response.ToResponse;
import com.sparta.soomtut.util.dto.request.PageRequestDto;
import com.sparta.soomtut.util.security.UserDetailsImpl;

import com.sparta.soomtut.review.service.ReviewService;

import com.sparta.soomtut.review.dto.request.CreateReviewRequestDto;

import lombok.RequiredArgsConstructor;


@RequestMapping("/review")
@RequiredArgsConstructor
@RestController
public class ReviewController {
    
    private final ReviewService reviewService;

    @PostMapping(value = "/create/{lectureRequestId}")
    public ResponseEntity<?> createReview(
            @PathVariable Long lectureRequestId,
            @RequestBody CreateReviewRequestDto reviewRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        var data = reviewService.createReview(lectureRequestId, reviewRequestDto, userDetails.getMember());
        return ToResponse.of(data, SuccessCode.REVIEW_CREATE_OK);
    }

    //리뷰 조회
    @GetMapping(value = "/{lectureRequestId}")
    public ResponseEntity<?> getReview(
        @PathVariable Long lectureRequestId)
    {
        var data = reviewService.getReview(lectureRequestId);
        return ToResponse.of(data, SuccessCode.REVIEW_GET_OK);
    }

    @PutMapping(value = "/{reviewId}")
    public ResponseEntity<?> updateReview(
        @PathVariable Long reviewId,
        @RequestBody CreateReviewRequestDto request,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        var data = reviewService.updateReview(reviewId, userDetails.getMemberId(), request);
        return ToResponse.of(data, SuccessCode.REVIEW_UPDATE_OK);
    }

    //리뷰 삭제
    @DeleteMapping(value = "/{reviewId}")
    public ResponseEntity<?> deleteReview(
        @PathVariable Long reviewId,
        @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        var data = reviewService.deleteReview(reviewId, userDetails.getMemberId());
 
        return ToResponse.of(data, SuccessCode.REVIEW_DELETE_OK);
    }

    // 게시된 강의의 리뷰 조회
    @GetMapping(value = "/lecture")
    public ResponseEntity<?> getReviewsByLecture(
        @RequestParam("lectureId") Long lectureId,
        @ModelAttribute PageRequestDto pageRequest)
    {
        var data = reviewService.getReviewsByLecture(lectureId, pageRequest.toPageable());
        return ToResponse.of(data, SuccessCode.REVIEW_GETBYLECTURE_OK);
    }

    // 해당 멤버의 리뷰 조회
    @GetMapping(value = "/member") 
    public ResponseEntity<?> getReviewsByMember(
        @RequestParam("memberId") Long memberId,
        @ModelAttribute PageRequestDto pageRequest)
    {
        var data = reviewService.getReviewsByMember(memberId, pageRequest.toPageable());
        return ToResponse.of(data, SuccessCode.REVIEW_GETBYMEMBER_OK);
    }

    // 나의 리뷰 조회
    @GetMapping(value = "/myReviews") 
    public ResponseEntity<?> getMyReviews(
        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
        @ModelAttribute PageRequestDto pageRequest)
    {
        var data = reviewService.getReviewsByMember(userDetailsImpl.getMemberId(), pageRequest.toPageable());
        return ToResponse.of(data, SuccessCode.REVIEW_GETBYMEMBER_OK);
    }

}