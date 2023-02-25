package com.sparta.soomtut.admin.service.impl;

import com.sparta.soomtut.review.entity.Review;
import com.sparta.soomtut.review.service.ReviewService;
import com.sparta.soomtut.admin.entity.DeleteReviewRequest;
import com.sparta.soomtut.admin.repository.DeleteReviewRequestRepository;
import com.sparta.soomtut.admin.service.DeleteReviewRequestService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteReviewRequestServiceImpl implements DeleteReviewRequestService {

    private final DeleteReviewRequestRepository deleteReviewRequestRepository;

    private final ReviewService reviewService;

    @Override
    @Transactional
    public String deleteReviewRequest(Long reviewId) {
        Review foundReview = reviewService.findReview(reviewId);
        deleteReviewRequestRepository.save(new DeleteReviewRequest(foundReview));
        return "해당 리뷰 삭제요청이 완료되었습니다!";
    }
}
