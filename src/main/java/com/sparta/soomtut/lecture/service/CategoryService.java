package com.sparta.soomtut.lecture.service;

import org.springframework.stereotype.Service;

import com.sparta.soomtut.util.response.ErrorCode;
import com.sparta.soomtut.lecture.entity.Category;
import com.sparta.soomtut.lecture.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(
            () -> new IllegalArgumentException(ErrorCode.NOT_FOUND_CATEGORY.getMessage())
        );
    }
}
