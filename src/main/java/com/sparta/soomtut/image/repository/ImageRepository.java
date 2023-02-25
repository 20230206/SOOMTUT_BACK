package com.sparta.soomtut.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.soomtut.image.entity.ImageEntity;

import java.util.List;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
    @Override
    List<ImageEntity> findAll();
}
