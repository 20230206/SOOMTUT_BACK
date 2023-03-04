package com.sparta.soomtut.admin.entity;

import com.sparta.soomtut.review.entity.Review;

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
    private Long reviewId;

    public DeleteReviewRequest(Review review) {
        this.reviewId = review.getId();
    }
}
