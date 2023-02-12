package com.sparta.soomtut.repository;

import com.sparta.soomtut.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByMemberId(Long memberId);
}
