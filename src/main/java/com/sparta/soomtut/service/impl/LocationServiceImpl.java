package com.sparta.soomtut.service.impl;

import com.sparta.soomtut.entity.Location;
import com.sparta.soomtut.entity.Member;
import com.sparta.soomtut.repository.LocationRepository;
import com.sparta.soomtut.service.interfaces.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    @Transactional
    public Location findMemberLocation(Long memberId){
        Location location = locationRepository.findByMemberId(memberId);
        if(location==null){
            throw new IllegalArgumentException("위치 정보값이 없습니다!");
        }
        return location;
    }


    public Location getLocation(Member member) {

        //아마 API에서 시,구,동을 넘겨줄텐데, 각각 넘겨 받아서 String address하나로 이어붙이거나 해야될듯..?
        //그래서 일단 위치 정보 객체 전부 받아오게 작성해놓겠습니다.
        //여기서 프로필이니까 x,y값은 빼고 줘야할듯 합니다.

        return findMemberLocation(member.getId());
    }
}
