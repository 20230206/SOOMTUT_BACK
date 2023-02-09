package com.sparta.soomtut.repository;

import com.sparta.soomtut.entity.Category;
import com.sparta.soomtut.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
