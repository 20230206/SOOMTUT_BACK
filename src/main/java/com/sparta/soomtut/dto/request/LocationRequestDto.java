package com.sparta.soomtut.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LocationRequestDto {

    private float vectorX;
    private float vectorY;
    private String address;

}
