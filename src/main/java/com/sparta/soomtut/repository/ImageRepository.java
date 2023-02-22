package com.sparta.soomtut.repository;

import com.sparta.soomtut.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
    @Override
    List<ImageEntity> findAll();
}
