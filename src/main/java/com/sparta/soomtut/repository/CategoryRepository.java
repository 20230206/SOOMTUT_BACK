package com.sparta.soomtut.repository;

import com.sparta.soomtut.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllBy();
}
