package com.sparta.soomtut.service.impl;

import com.sparta.soomtut.lectureRequest.repository.LectureRequestRepository;
import com.sparta.soomtut.lectureRequest.entity.LectureRequest;
import com.sparta.soomtut.lectureRequest.entity.LectureState;
import com.sparta.soomtut.lectureRequest.repository.LectureRequestRepository;
import com.sparta.soomtut.review.dto.request.CreateReviewRequestDto;
import com.sparta.soomtut.review.entity.Review;
import com.sparta.soomtut.review.repository.ReviewRepository;
import com.sparta.soomtut.review.service.impl.ReviewServiceImpl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {


    @Mock
    LectureRequestRepository lectureRequestRepository;

    @Mock
    ReviewRepository reviewRepository;

    @InjectMocks
    ReviewServiceImpl reviewService;

    // @Test
    // @DisplayName("신청강좌 찾기")
    // void findTuitionRequest() {

    //     LectureRequest tuitionRequest = new LectureRequest(LectureState.DONE,1L,1L);
    //     given(lectureRequestRepository.findByPostIdAndTuteeId(anyLong(),anyLong())).willReturn(Optional.ofNullable(tuitionRequest));

    //     LectureRequest tut = reviewService.findTuitionRequest(1L,1L);

    //     assertThat(tuitionRequest.getPostId()).isEqualTo(tut.getPostId());
    // }

    // @Test
    // @DisplayName("리뷰 저장하기(ReviewServiceImpl)")
    // void saveReview() {
    //     Review review = new Review(1L,1L,3f,"리뷰");
    //     given(reviewRepository.save(any())).willReturn(review);
    //     Review savedReview = reviewService.createReview(1L,new CreateReviewRequestDto(3f,"리뷰"),1L);
    //     assertThat(review.getReview_content()).isEqualTo(savedReview.getReview_content());
    // }


    // @Test
    // @DisplayName("완료된 강좌인지 판단하기")
    // void checkTuitionState() {
    //     LectureRequest tuitionRequest = new LectureRequest(LectureState.DONE,1L,1L);
    //     //given(reviewService.findTuitionRequest(any(),any())).willReturn(tuitionRequest);
    //     tuitionRequest.changeTuitionState();
    //     given(lectureRequestRepository.findByPostIdAndTuteeId(anyLong(),anyLong())).willReturn(Optional.ofNullable(tuitionRequest));

    //     assertThat(reviewService.checkTuitionState(anyLong(),anyLong())).isEqualTo(true);
    // }

    // @Test
    // @DisplayName("Id로 리뷰찾기")
    // void findReviewById(){
    //     Review review = new Review(1L,1L,3f,"굿");

    //     given(reviewRepository.findById(anyLong())).willReturn(Optional.ofNullable(review));

    //     Review foundReview = reviewService.findReviewById(anyLong());

    //     assertThat(foundReview.getReview_content()).isEqualTo("굿");
    // }


}