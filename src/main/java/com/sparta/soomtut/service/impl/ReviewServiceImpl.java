package com.sparta.soomtut.service.impl;

import com.sparta.soomtut.dto.request.CreateReviewRequestDto;
import com.sparta.soomtut.dto.request.PageRequestDto;
import com.sparta.soomtut.entity.Review;
import com.sparta.soomtut.entity.TuitionRequest;
import com.sparta.soomtut.enums.TuitionState;
import com.sparta.soomtut.exception.ErrorCode;
import com.sparta.soomtut.repository.ReviewRepository;
import com.sparta.soomtut.repository.TuitionRequestRepository;
import com.sparta.soomtut.service.interfaces.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final TuitionRequestRepository tuitionRequestRepository;
    private final ReviewRepository reviewRepository;

    @Override
    @Transactional(readOnly = true)
    public TuitionRequest findTuitionRequest(Long postId, Long tuteeId) {
        TuitionRequest tuitionRequest = tuitionRequestRepository.findByPostIdAndTuteeId(postId, tuteeId).orElseThrow(
                () -> new IllegalArgumentException("신청한 강좌 목록이 없습니다!")
        );
        return tuitionRequest;
    }
    @Transactional
    public Review findReviewById(Long reviewId){
        return reviewRepository.findById(reviewId).orElseThrow(
                ()-> new IllegalArgumentException(ErrorCode.NOT_FOUND_REVIEW.getMessage())
        );
    }



    @Override
    @Transactional(readOnly = true)
    public Page<Review> findReviewByTutorId(PageRequestDto pageRequestDto, Long tutorId){
        Pageable pageable = PageRequest.of(pageRequestDto.getPage(),pageRequestDto.getSize());
        Page<Review> reviews = reviewRepository.findAllByTutorId(tutorId,pageable);

        return reviews;


    }

    @Override
    @Transactional
    public Review saveReview(Long tutorId, CreateReviewRequestDto reviewRequestDto, Long tuteeId) {

        return reviewRepository.save(new Review(tutorId, tuteeId, reviewRequestDto.getStar_rating(), reviewRequestDto.getReview_content()));


    }

    @Override
    public Page<Review> getReview(PageRequestDto pageRequestDto, Long tutorId) {

        return findReviewByTutorId(pageRequestDto,tutorId);
    }


    @Override
    @Transactional
    public boolean checkTuitionState(Long postId, Long tuteeId) {

        TuitionState tuitionState = findTuitionRequest(postId, tuteeId).getTuitionState();
        if (tuitionState.equals(TuitionState.IN_PROGRESS)) {
            return false;
        }
        return true;

    }
    @Override
    public Review findReview(Long reviewId) {

        return findReviewById(reviewId);
    }
}
