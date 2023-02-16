package com.sparta.soomtut.controller;

import com.sparta.soomtut.dto.request.LocationRequestDto;
import com.sparta.soomtut.entity.Location;
import com.sparta.soomtut.entity.Member;
import com.sparta.soomtut.repository.LocationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class LocationTestController {

    private final LocationRepository locationRepository;

    @RequestMapping(value = "/")
    public String locationTest(){
        return "/map_view";
    }
    @Transactional
    @ResponseBody
    @PostMapping(value = "/location")
    public String saveLocation(@RequestBody LocationRequestDto locationRequestDto){
        float x = locationRequestDto.getVectorX();
        float y = locationRequestDto.getVectorY();
        String address = locationRequestDto.getAddress();
        locationRepository.save(new Location(address,x,y));
        return "저장이 완료되었습니다!";
    }
}
