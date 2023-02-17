package com.sparta.soomtut.repository;

import com.sparta.soomtut.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
        Page<Review> findAllByTutorId(Long tutorId, Pageable pageable);
}
