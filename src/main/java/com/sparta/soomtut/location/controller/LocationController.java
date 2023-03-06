package com.sparta.soomtut.location.controller;

import com.sparta.soomtut.location.dto.request.LocationRequest;
import com.sparta.soomtut.location.dto.response.LocationResponse;
import com.sparta.soomtut.location.entity.Location;
import com.sparta.soomtut.location.service.LocationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.sparta.soomtut.util.security.UserDetailsImpl;

import org.springframework.http.ResponseEntity;

import java.util.List;

@RequestMapping("/location")
@RequiredArgsConstructor
@RestController
public class LocationController {
    private final LocationService locationService;

    @Transactional
    @PutMapping(value = "/updatelocation")
    public ResponseEntity<?> saveLocation(
        @RequestBody LocationRequest locationRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        var data = locationService.updateLocation(locationRequestDto, userDetails.getMember());
        return ResponseEntity.ok().body(data);
    }

    @Transactional
    @GetMapping("/showNearTutor")
    public List<LocationResponse> getNearTutor(
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        Location myLocation = locationService.getLocation(userDetails.getMember());
        List<LocationResponse> otherLocation =  locationService.getAllLocation(myLocation);
        return otherLocation;
    }

}
