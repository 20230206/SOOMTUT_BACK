package com.sparta.soomtut.lecture.dto.request;

import lombok.Getter;

@Getter
public class UpdatePostRequestDto {
    private String title;
    private String image;
    private String content;
    private int fee;
}
