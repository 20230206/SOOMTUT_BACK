package com.sparta.soomtut.review.service.impl;

import com.sparta.soomtut.lecture.entity.Lecture;
import com.sparta.soomtut.lecture.service.LectureService;
import com.sparta.soomtut.lectureRequest.entity.LectureRequest;
import com.sparta.soomtut.lectureRequest.entity.LectureState;
import com.sparta.soomtut.lectureRequest.repository.LectureRequestRepository;
import com.sparta.soomtut.review.dto.request.CreateReviewRequestDto;
import com.sparta.soomtut.review.entity.Review;
import com.sparta.soomtut.review.repository.ReviewRepository;
import com.sparta.soomtut.review.service.ReviewService;
import com.sparta.soomtut.util.dto.request.PageRequestDto;
import com.sparta.soomtut.util.response.ErrorCode;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {
    private final LectureRequestRepository lectureRequestRepository;
    private final LectureService lectureService;
    private final ReviewRepository reviewRepository;

    @Override
    @Transactional(readOnly = true)
    public LectureRequest findTuitionRequest(Long lectureId, Long tuteeId) {
        Lecture lecture = lectureService.getLectureById(lectureId);
        return lectureRequestRepository.findByLectureAndTuteeId(lecture, tuteeId).orElseThrow(
                () -> new IllegalArgumentException("신청한 강좌 목록이 없습니다!")
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Review> findReviewByTutorId(PageRequestDto pageRequestDto, Long tutorId) {
        Pageable pageable = PageRequest.of(pageRequestDto.getPage(),pageRequestDto.getSize());
        return reviewRepository.findAllByTutorId(tutorId,pageable);
    }

    @Override
    @Transactional
    public Review saveReview(
            Long tutorId, CreateReviewRequestDto reviewRequestDto, Long tuteeId)
    {
        return reviewRepository.save(new Review(tutorId, tuteeId, reviewRequestDto.getStar_rating(), reviewRequestDto.getReview_content()));
    }

    @Override
    @Transactional
    public boolean checkTuitionState(Long postId, Long tuteeId) {
        LectureState lectureState = findTuitionRequest(postId, tuteeId).getLectureState();
        return !lectureState.equals(LectureState.IN_PROGRESS);
    }

    @Override
    public Review findReview(Long reviewId) {
        return findReviewById(reviewId);
    }

    @Override
    public Page<Review> getReview(PageRequestDto pageRequestDto, Long tutorId) {
        return findReviewByTutorId(pageRequestDto,tutorId);
    }

    @Transactional
    public Review findReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(
                ()-> new IllegalArgumentException(ErrorCode.NOT_FOUND_REVIEW.getMessage()));
    }

}
