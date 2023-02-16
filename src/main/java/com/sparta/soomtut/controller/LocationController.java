package com.sparta.soomtut.controller;

import com.sparta.soomtut.dto.request.LocationRequestDto;
import com.sparta.soomtut.entity.Location;
import com.sparta.soomtut.repository.LocationRepository;
import com.sparta.soomtut.service.interfaces.LocationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.sparta.soomtut.util.security.UserDetailsImpl;

import org.springframework.http.ResponseEntity;

@RestController
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @Transactional
    @PutMapping(value = "/location")
    public ResponseEntity<?> saveLocation(
        @RequestBody LocationRequestDto locationRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        var data = locationService.updateLocation(locationRequestDto, userDetails.getMember());
        return ResponseEntity.ok().body(data);
    }
}
