package com.sparta.soomtut.service.impl;

import org.springframework.stereotype.Service;

import com.sparta.soomtut.repository.CategoryRepository;
import com.sparta.soomtut.exception.ErrorCode;
import com.sparta.soomtut.entity.Category;

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
