package com.sparta.soomtut.location.repository;

import com.sparta.soomtut.location.dto.response.LocationResponseDto;
import com.sparta.soomtut.location.entity.Location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByMemberId(Long memberId);

     @Query("select new com.sparta.soomtut.location.dto.response.LocationResponseDto(l.address,l.vectorX,l.vectorY,m.nickname)  from Location l join Member m on l.member.id = m.id where l.address LIKE :myCityName%")
     List<LocationResponseDto> findAllByAddress(String myCityName);
}
