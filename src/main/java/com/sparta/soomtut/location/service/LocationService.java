package com.sparta.soomtut.location.service;

import com.sparta.soomtut.location.dto.request.LocationRequestDto;
import com.sparta.soomtut.location.dto.response.LocationResponseDto;
import com.sparta.soomtut.location.entity.Location;
import com.sparta.soomtut.member.entity.Member;
import com.sparta.soomtut.auth.dto.request.RegisterRequest;

import java.util.List;

public interface LocationService {

    Location findMemberLocation(Long memberId);
    Location updateLocation(LocationRequestDto locationRequestDto, Member member);

    Location getLocation(Member member);
    Location saveLocation(RegisterRequest requestDto, Member member);
    Location saveLocation(Location location);

    List<LocationResponseDto> getAllLocation(Location myLocation);
    
}
