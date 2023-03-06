package com.sparta.soomtut.location.service;

import com.sparta.soomtut.location.dto.request.LocationRequest;
import com.sparta.soomtut.location.dto.response.LocationResponse;
import com.sparta.soomtut.location.entity.Location;
import com.sparta.soomtut.member.entity.Member;
import java.util.List;

public interface LocationService {
    Location saveLocation(LocationRequest requestDto);
    Location updateLocation(LocationRequest locationRequestDto, Member member);

    Location findMemberLocation(Long memberId);
    Location getLocation(Member member);
    List<LocationResponse> getAllLocation(Location myLocation);
    
}
