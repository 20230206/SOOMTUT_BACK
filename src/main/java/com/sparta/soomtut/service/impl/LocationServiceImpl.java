package com.sparta.soomtut.service.impl;

import com.sparta.soomtut.dto.response.LocationResponseDto;
import com.sparta.soomtut.entity.Location;
import com.sparta.soomtut.member.entity.Member;
import com.sparta.soomtut.repository.LocationRepository;
import com.sparta.soomtut.service.interfaces.LocationService;
import com.sparta.soomtut.util.response.ErrorCode;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sparta.soomtut.dto.request.LocationRequestDto;
import com.sparta.soomtut.dto.request.RegisterRequest;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    @Transactional
    public Location findMemberLocation(Long memberId){
        Location location = locationRepository.findByMemberId(memberId).orElseThrow(
                () -> new IllegalArgumentException(ErrorCode.NOT_FOUND_LOCATION.getMessage())
        );
        return location;
    }


    public Location getLocation(Member member) {

        //아마 API에서 시,구,동을 넘겨줄텐데, 각각 넘겨 받아서 String address하나로 이어붙이거나 해야될듯..?
        //그래서 일단 위치 정보 객체 전부 받아오게 작성해놓겠습니다.
        //여기서 프로필이니까 x,y값은 빼고 줘야할듯 합니다.

        return findMemberLocation(member.getId());
    }

    @Override
    @Transactional
    public Location updateLocation(LocationRequestDto locationRequestDto, Member member){ 

            Location location = findMemberLocation(member.getId());
            location.updateLocation(locationRequestDto);
            
        return location;
    }


    @Override
    @Transactional
    public Location saveLocation(RegisterRequest requestDto, Member member) {
        return locationRepository.save(Location.forNewMember()
                    .member(member)
                    .address(requestDto.getAddress())
                    .vectorX(requestDto.getVectorX())
                    .vectorY(requestDto.getVectorY())
                    .build());
    }


    @Override
    @Transactional
    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public List<LocationResponseDto> getAllLocation(Location myLocation) {
        String myCityName = myLocation.getAddress().split(" ")[0];
        List<LocationResponseDto> cityUserLocations = locationRepository.findAllByAddress(myCityName);
        return cityUserLocations;
    }
    
}
