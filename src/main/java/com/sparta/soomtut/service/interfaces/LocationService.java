package com.sparta.soomtut.service.interfaces;

import com.sparta.soomtut.entity.Location;
import com.sparta.soomtut.entity.Member;

public interface LocationService {

    Location findMemberLocation(Long memberId);

    Location getLocation(Member member);
}
