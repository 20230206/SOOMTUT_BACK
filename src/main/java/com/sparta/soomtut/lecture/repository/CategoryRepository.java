package com.sparta.soomtut.lecture.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.soomtut.lecture.entity.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllBy();
}
