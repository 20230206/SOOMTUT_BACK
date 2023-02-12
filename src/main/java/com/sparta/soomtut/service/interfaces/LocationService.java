package com.sparta.soomtut.service.interfaces;

import com.sparta.soomtut.entity.Location;

public interface LocationService {

    Location findMemberLocation(Long memberId);
}
