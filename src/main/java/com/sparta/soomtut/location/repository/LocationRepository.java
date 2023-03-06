package com.sparta.soomtut.location.repository;

import com.sparta.soomtut.location.dto.response.LocationResponse;
import com.sparta.soomtut.location.entity.Location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    // TODO : 변경
    // @Query("select new com.sparta.soomtut.location.dto.response.LocationResponseDto(l.address,l.vectorX,l.vectorY,m.nickname,m.image,m.email,m.createdAt,m.id)  from Location l join Member m on l.member.id = m.id where l.address LIKE :myCityName% AND m.state!=com.sparta.soomtut.member.entity.enums.MemberState.SUSPEND")
    // List<LocationResponse> findAllByAddress(@Param("myCityName") String myCityName);

}
