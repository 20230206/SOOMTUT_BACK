package com.sparta.soomtut.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;

// lombok
@Getter
@NoArgsConstructor

// jpa
@Entity

public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long tutorId;


    private Long tuteeId;

    @Column(nullable = false)
    private float star_rating;

    @Column(nullable = false)
    private String review_content;

    public Review(Long tutorId, Long tuteeId, float star_rating, String review_content) {
        this.tutorId = tutorId;
        this.tuteeId = tuteeId;
        this.star_rating = star_rating;
        this.review_content = review_content;
    }


}
