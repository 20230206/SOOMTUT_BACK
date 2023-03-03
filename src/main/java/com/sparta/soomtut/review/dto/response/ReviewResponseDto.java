package com.sparta.soomtut.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.sparta.soomtut.review.entity.Review;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDto {
    private Long reviewId;
    private Long lectureId;
    private Long lectureRequestId;
    private float starScore;
    private String content;

    public static ReviewResponseDto toDto(Review review) {
        return ReviewResponseDto.builder()
                .reviewId(review.getId())
                .lectureId(review.getLectureId())
                .lectureRequestId(review.getLectureRequest().getId())
                .starScore(review.getStar_rating())
                .content(review.getReview_content())
                .build();
    }
}
