package com.sparta.soomtut.lecture.entity;

import com.sparta.soomtut.lecture.dto.request.CategoryRequestDto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

// lombok
@Getter
@NoArgsConstructor

// jpa
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public Category(CategoryRequestDto categoryRequestDto) {
        this.name = categoryRequestDto.getName();
    }
}
