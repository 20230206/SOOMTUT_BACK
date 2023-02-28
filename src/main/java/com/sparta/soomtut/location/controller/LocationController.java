package com.sparta.soomtut.location.controller;

import com.sparta.soomtut.location.dto.request.LocationRequestDto;
import com.sparta.soomtut.location.dto.response.LocationResponseDto;
import com.sparta.soomtut.location.entity.Location;
import com.sparta.soomtut.location.service.LocationService;
import com.sparta.soomtut.util.dto.request.PageRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.sparta.soomtut.util.security.UserDetailsImpl;

import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @Transactional
    @PutMapping(value = "/updatelocation")
    public ResponseEntity<?> saveLocation(
        @RequestBody LocationRequestDto locationRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        var data = locationService.updateLocation(locationRequestDto, userDetails.getMember());
        return ResponseEntity.ok().body(data);
    }

    @Transactional
    @GetMapping("/showNearTutor")
    public List<LocationResponseDto> getNearTutor(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        Location myLocation = locationService.getLocation(userDetails.getMember());
        List<LocationResponseDto> otherLocation =  locationService.getAllLocation(myLocation);

        return otherLocation;
    }
}
