package com.sparta.soomtut.service.impl;

import com.sparta.soomtut.dto.CreateReviewRequestDto;
import com.sparta.soomtut.entity.Review;
import com.sparta.soomtut.entity.TuitionRequest;
import com.sparta.soomtut.enums.TuitionState;
import com.sparta.soomtut.repository.ReviewRepository;
import com.sparta.soomtut.repository.TuitionRequestRepository;
import com.sparta.soomtut.service.interfaces.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final TuitionRequestRepository tuitionRequestRepository;
    private final ReviewRepository reviewRepository;

    @Override
    @Transactional
    public TuitionRequest findTuitionRequest(Long postId, Long tuteeId) {
        TuitionRequest tuitionRequest = tuitionRequestRepository.findByPostIdAndTuteeId(postId, tuteeId).orElseThrow(
                () -> new IllegalArgumentException("신청한 강좌 목록이 없습니다!")
        );
        return tuitionRequest;
    }

    @Override
    @Transactional
    public Review saveReview(Long tutorId, CreateReviewRequestDto reviewRequestDto, Long tuteeId) {

        return reviewRepository.save(new Review(tutorId, tuteeId, reviewRequestDto.getStar_rating(), reviewRequestDto.getReview_content()));


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
}
