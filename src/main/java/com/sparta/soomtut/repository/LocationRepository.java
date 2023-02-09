package com.sparta.soomtut.repository;

import com.sparta.soomtut.entity.Location;
import com.sparta.soomtut.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
