package com.sparta.soomtut.service.impl;

import com.sparta.soomtut.entity.Review;
import com.sparta.soomtut.repository.DeleteReviewRequestRepository;
import com.sparta.soomtut.service.interfaces.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class DeleteReviewRequestServiceImplTest {

    @Mock
    DeleteReviewRequestRepository deleteReviewRequestRepository;

    @Mock
    ReviewService reviewService;

    @InjectMocks
    DeleteReviewRequestServiceImpl deleteReviewRequestService;
    @Test
    void deleteReviewRequest() {
        Review review = new Review(1L,1L,3f,"굿");
        given(reviewService.findReview(anyLong())).willReturn(review);

        String msg = deleteReviewRequestService.deleteReviewRequest(anyLong());

        assertThat(msg).isEqualTo("해당 리뷰 삭제요청이 완료되었습니다!");

    }
}