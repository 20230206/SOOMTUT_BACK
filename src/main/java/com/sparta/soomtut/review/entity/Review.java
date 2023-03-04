package com.sparta.soomtut.review.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.sparta.soomtut.lectureRequest.entity.LectureRequest;
import com.sparta.soomtut.review.dto.request.CreateReviewRequestDto;

@Getter
@NoArgsConstructor
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long tuteeId;

    @Column(nullable = false)
    private Long lectureId;

    @Column(nullable = false)
    private float star_rating;

    @Column(nullable = false)
    private String review_content;

    @OneToOne
    @JoinColumn(name="lecture_request_id")
    private LectureRequest lectureRequest;

    public Review(LectureRequest lectureRequest, CreateReviewRequestDto requestDto) {
        this.tuteeId = lectureRequest.getTuteeId();
        this.lectureId = lectureRequest.getLectureId();
        this.star_rating = requestDto.getStar_rating();
        this.review_content = requestDto.getReview_content();
        this.lectureRequest = lectureRequest;
    }
    
    public void updateReview(CreateReviewRequestDto request) {
        this.star_rating = request.getStar_rating();
        this.review_content = request.getReview_content();
    }
}
