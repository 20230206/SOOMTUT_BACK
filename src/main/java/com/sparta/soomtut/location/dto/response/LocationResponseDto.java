package com.sparta.soomtut.location.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocationResponseDto {

    private String address;
    private float vectorX;
    private float vectorY;

    private String nickname;
    private String image;
}