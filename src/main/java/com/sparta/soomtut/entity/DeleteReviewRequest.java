package com.sparta.soomtut.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
@Entity
public class DeleteReviewRequest {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long tutorId;

    @Column
    private Long reviewId;

    public DeleteReviewRequest(Review review) {
        this.tutorId = review.getTutorId();
        this.reviewId = review.getId();
    }
}
