package com.sparta.soomtut.location.service;

import com.sparta.soomtut.location.dto.request.LocationRequest;
import com.sparta.soomtut.location.entity.Location;
import com.sparta.soomtut.member.entity.Member;

public interface LocationService {
    Location saveLocation(LocationRequest request);
    Location saveLocation(Location location);

    Location updateLocation(Long locationId, LocationRequest request);

    Location getLocationById(Long memberId);
    Location getLocation(Member member);
}
