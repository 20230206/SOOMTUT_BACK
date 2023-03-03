package com.sparta.soomtut.review.service.impl;

import com.sparta.soomtut.lecture.service.LectureService;
import com.sparta.soomtut.lectureRequest.service.LectureRequestService;
import com.sparta.soomtut.review.dto.request.CreateReviewRequestDto;
import com.sparta.soomtut.review.entity.Review;
import com.sparta.soomtut.member.entity.Member;
import com.sparta.soomtut.review.dto.response.ReviewResponseDto;

import com.sparta.soomtut.review.repository.ReviewRepository;

import com.sparta.soomtut.review.service.ReviewService;
import com.sparta.soomtut.util.dto.request.PageRequestDto;
import com.sparta.soomtut.util.response.ErrorCode;
import com.sparta.soomtut.util.exception.CustomException;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

        return ReviewResponseDto.toDto(reviewRepository.save(new Review(lectureRequest, requestDto)));
    }

    @Override
    public ReviewResponseDto getReview(Long lectureRequestId) {
        Review review = getReviewByLectureRequestId(lectureRequestId);

        return ReviewResponseDto.toDto(review);
    }

    @Transactional(readOnly = true)
    public Review getReviewByLectureRequestId(Long lectureRequestId) {
        return reviewRepository.findByLectureRequestId(lectureRequestId).orElseThrow(
            () -> new CustomException(ErrorCode.REVIEW_NOT_FOUND)
        );
    }


}
