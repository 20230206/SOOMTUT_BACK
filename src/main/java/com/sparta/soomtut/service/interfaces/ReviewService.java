package com.sparta.soomtut.service.interfaces;

import com.sparta.soomtut.dto.CreateReviewRequestDto;
import com.sparta.soomtut.entity.Review;
import com.sparta.soomtut.entity.TuitionRequest;

public interface ReviewService {

    TuitionRequest findTuitionRequest(Long postId, Long tuteeId);

    boolean checkTuitionState(Long postId, Long tuteeId);

    Review saveReview(Long tutorId, CreateReviewRequestDto reviewRequestDto, Long id);
}
