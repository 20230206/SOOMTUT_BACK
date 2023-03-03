package com.sparta.soomtut.location.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
    private String email;
    private LocalDate createdAt;
    private Long memberId;

}