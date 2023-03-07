package com.sparta.soomtut.location.controller;

import com.sparta.soomtut.location.dto.request.LocationRequest;
import com.sparta.soomtut.location.dto.response.LocationResponse;
import com.sparta.soomtut.location.service.LocationService;
import com.sparta.soomtut.util.response.SuccessCode;
import com.sparta.soomtut.util.response.ToResponse;
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
        var data = locationService.updateLocation(userDetails.getMember().getLocation().getId(), locationRequestDto);
        return ToResponse.of(data, SuccessCode.LOCATION_SAVE_OK);
    }

}
