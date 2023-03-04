package com.sparta.soomtut.review.service.impl;

import com.sparta.soomtut.lectureRequest.service.LectureRequestService;
import com.sparta.soomtut.review.dto.request.CreateReviewRequestDto;
import com.sparta.soomtut.review.entity.Review;
import com.sparta.soomtut.member.entity.Member;
import com.sparta.soomtut.review.dto.response.ReviewResponseDto;

import com.sparta.soomtut.review.repository.ReviewRepository;

import com.sparta.soomtut.review.service.ReviewService;
import com.sparta.soomtut.util.response.ErrorCode;
import com.sparta.soomtut.util.exception.CustomException;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    
    private final LectureRequestService lectureRequestService;

    @Override
    @Transactional
    public ReviewResponseDto createReview(Long lectureRequestId, CreateReviewRequestDto requestDto, Member member)
    {   
        var lectureRequest = lectureRequestService.getLectureRequestById(lectureRequestId);
        lectureRequest.updateReview();

        Review review = reviewRepository.save(new Review(lectureRequest, requestDto));

        return ReviewResponseDto.toDto().review(review).build();
    }

    @Override
    public ReviewResponseDto getReview(Long lectureRequestId) {
        Review review = getReviewByLectureRequestId(lectureRequestId);

        return ReviewResponseDto.toDto().review(review).build();
    }

    @Override
    @Transactional
    public ReviewResponseDto updateReview(Long reviewId, Long memberId, CreateReviewRequestDto request) {
        Review review = getReviewById(reviewId);
        
        if(review.getTuteeId() != memberId) throw new CustomException(ErrorCode.MISMATCH_CREATOR);

        review.updateReview(request);

        return ReviewResponseDto.toDto().review(review).build();
        
    }

    @Transactional(readOnly = true)
    public Review getReviewByLectureRequestId(Long lectureRequestId) {
        return reviewRepository.findByLectureRequestId(lectureRequestId).orElseThrow(
            () -> new CustomException(ErrorCode.REVIEW_NOT_FOUND)
        );
    }

    @Override
    public Page<ReviewResponseDto> getReviewsByMember(Long memberId, Pageable pageable) {
        Page<Review> reviews = getReviewsByMemberId(memberId, pageable);
        
        return reviews.map(item -> ReviewResponseDto.toDto().review(item).build());
    }

    @Transactional(readOnly=true)
    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND_REVIEW)
        );
    }

    @Transactional(readOnly=true)
    public Page<Review> getReviewsByMemberId(Long memberId, Pageable pageable) {
        return reviewRepository.findAllByTuteeId(memberId, pageable);
    }

    @Override
    @Transactional
    public ReviewResponseDto deleteReview(Long reviewId, Long memberId) {
        Review review = this.getReviewById(reviewId);
        if(review.getTuteeId() != memberId) throw new CustomException(ErrorCode.NOT_FOUND_REVIEW);

        var response = ReviewResponseDto.toDto().review(review).build();
        reviewRepository.delete(review);

        return response;
        
    }

}
