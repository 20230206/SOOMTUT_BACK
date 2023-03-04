package com.sparta.soomtut.review.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.sparta.soomtut.lectureRequest.dto.LecReqResponseDto;
import com.sparta.soomtut.review.entity.Review;

@Getter
@NoArgsConstructor
public class ReviewResponseDto {
    private Long id;
    private float starScore;
    private String contents;

    private LecReqResponseDto lectureRequest;

    @Builder(builderClassName = "ReviewToDto", builderMethodName="toDto")
    public ReviewResponseDto (Review review){
        this.id = review.getId();
        this.starScore = review.getStar_rating();
        this.contents = review.getReview_content();

        this.lectureRequest = LecReqResponseDto.toDto().lectureRequest(review.getLectureRequest()).build();
    }
}
