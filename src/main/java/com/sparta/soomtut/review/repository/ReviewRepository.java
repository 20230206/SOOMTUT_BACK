package com.sparta.soomtut.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.soomtut.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

        Page<Review> findAllByTutorId(Long tutorId, Pageable pageable);

}
