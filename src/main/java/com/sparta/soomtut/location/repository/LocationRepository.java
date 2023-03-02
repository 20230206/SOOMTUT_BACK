package com.sparta.soomtut.location.repository;

import com.sparta.soomtut.location.dto.response.LocationResponseDto;
import com.sparta.soomtut.location.entity.Location;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByMemberId(Long memberId);


     @Query("select new com.sparta.soomtut.location.dto.response.LocationResponseDto(l.address,l.vectorX,l.vectorY,m.nickname,m.image,m.email,m.createdAt,m.id)  from Location l join Member m on l.member.id = m.id where l.address LIKE :myCityName%")
     List<LocationResponseDto> findAllByAddress(@Param("myCityName") String myCityName);
}
