package com.sparta.soomtut.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.soomtut.review.entity.Review;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByLectureRequestId(Long lectureRequestId);

    Page<Review> findAllByTuteeIdAndDeletedIsFalse(Long memberId, Pageable pageable);
}
