package com.sparta.soomtut.location.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LocationUpdateRequest {
    private float vectorX;
    private float vectorY;
    private String address;
    private String sido;
    private String sigungu;
    private String bname;
}
