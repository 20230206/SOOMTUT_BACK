package com.sparta.soomtut.location.repository;

import com.sparta.soomtut.location.dto.response.LocationResponse;
import com.sparta.soomtut.location.entity.Location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    // TODO : 변경


}
