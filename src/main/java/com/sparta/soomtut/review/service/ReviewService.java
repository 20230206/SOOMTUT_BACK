package com.sparta.soomtut.review.service;

import com.sparta.soomtut.review.dto.request.CreateReviewRequestDto;
import com.sparta.soomtut.review.dto.response.ReviewResponseDto;
import com.sparta.soomtut.member.entity.Member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ReviewService {

    ReviewResponseDto createReview(Long lectureRequestId, CreateReviewRequestDto request, Member member);
    ReviewResponseDto getReview(Long lectureRequestId);
    ReviewResponseDto updateReview(Long reviewId, Long memberId, CreateReviewRequestDto request);

    Page<ReviewResponseDto> getReviewsByMember(Long memberId, Pageable pageable);

}
