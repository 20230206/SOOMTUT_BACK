package com.sparta.soomtut.review.service;

import com.sparta.soomtut.review.dto.request.CreateReviewRequestDto;
import com.sparta.soomtut.review.dto.response.ReviewResponseDto;
import com.sparta.soomtut.review.entity.Review;
import com.sparta.soomtut.member.entity.Member;
import com.sparta.soomtut.util.dto.request.PageRequestDto;

import org.springframework.data.domain.Page;


public interface ReviewService {

    ReviewResponseDto createReview(Long lectureRequestId, CreateReviewRequestDto request, Member member);
    ReviewResponseDto getReview(Long lectureRequestId);

}
