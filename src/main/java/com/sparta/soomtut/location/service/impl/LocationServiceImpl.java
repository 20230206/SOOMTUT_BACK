package com.sparta.soomtut.location.service.impl;

import com.sparta.soomtut.member.entity.Member;
import com.sparta.soomtut.location.dto.request.LocationRequest;
import com.sparta.soomtut.location.dto.response.LocationResponse;
import com.sparta.soomtut.location.entity.Location;
import com.sparta.soomtut.location.repository.LocationRepository;
import com.sparta.soomtut.location.service.LocationService;
import com.sparta.soomtut.util.response.ErrorCode;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    // 일반 회원 등록 시 새로운 주소지 등록
    @Override
    @Transactional
    public Location saveLocation(LocationRequest request) {
        return locationRepository.save(Location.builder().request(request).build());
    }

    // OAuth 회원 등록 시 새로운 주소지 등록
    @Override
    @Transactional
    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    // 주소 업데이트
    @Override
    @Transactional
    public Location updateLocation(Long locationId, LocationRequest request) {
        Location location = getLocationById(locationId);
        location.updateLocation(request);
        return location;
    }


    @Override
    @Transactional
    public Location getLocationById(Long locationId) {
        return locationRepository.findById(locationId).orElseThrow(
                () -> new IllegalArgumentException(ErrorCode.NOT_FOUND_LOCATION.getMessage()));
    }

    public Location getLocation(Member member) {
        //아마 API에서 시,구,동을 넘겨줄텐데, 각각 넘겨 받아서 String address하나로 이어붙이거나 해야될듯..?
        //그래서 일단 위치 정보 객체 전부 받아오게 작성해놓겠습니다.
        //여기서 프로필이니까 x,y값은 빼고 줘야할듯 합니다.
        return getLocationById(member.getId());
    }

    @Override
    @Transactional(readOnly=true)
    public List<LocationResponse> getAllLocation(Location myLocation) {
        // TODO : 변경
        // String myCityName = myLocation.getAddress();
        // String myCityNameFirst = myCityName.split(" ")[0];
        // List<LocationResponse> cityUserLocations = locationRepository.findAllByAddress(myCityNameFirst);
        // cityUserLocations.stream()
        //         .filter(s->s.getAddress().equals(myCityName))
        //         .toList()
        //         .forEach(cityUserLocations::remove);
        return null;
    }
    
}
