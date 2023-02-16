package com.sparta.soomtut.service.interfaces;

import com.sparta.soomtut.entity.Location;
import com.sparta.soomtut.entity.Member;
import com.sparta.soomtut.dto.request.LocationRequestDto;
import com.sparta.soomtut.dto.request.SignupRequestDto;

public interface LocationService {

    Location findMemberLocation(Long memberId);
    Location updateLocation(LocationRequestDto locationRequestDto, Member member);

    Location getLocation(Member member);
    Location saveLocation(SignupRequestDto requestDto, Member member);
}
