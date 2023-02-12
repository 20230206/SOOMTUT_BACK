package com.sparta.soomtut.repository;

import com.sparta.soomtut.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findByMemberId(Long memberId);
}
