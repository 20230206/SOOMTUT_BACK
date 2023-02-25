package com.sparta.soomtut.review.service;

import com.sparta.soomtut.dto.request.CreateReviewRequestDto;
import com.sparta.soomtut.dto.request.PageRequestDto;
import com.sparta.soomtut.lectureRequest.entity.TuitionRequest;
import com.sparta.soomtut.review.entity.Review;

import org.springframework.data.domain.Page;


public interface ReviewService {

    TuitionRequest findTuitionRequest(Long postId, Long tuteeId);

    boolean checkTuitionState(Long postId, Long tuteeId);

    Review saveReview(Long tutorId, CreateReviewRequestDto reviewRequestDto, Long id);
    Page<Review> getReview(PageRequestDto pageRequestDto, Long tutorId);
    Page<Review> findReviewByTutorId(PageRequestDto pageRequestDto, Long tutorId);

    Review findReview(Long reviewId);
}
