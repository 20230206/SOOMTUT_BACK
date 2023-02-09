package com.sparta.soomtut.repository;

import com.sparta.soomtut.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
