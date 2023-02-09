package com.sparta.soomtut.repository;

import com.sparta.soomtut.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
