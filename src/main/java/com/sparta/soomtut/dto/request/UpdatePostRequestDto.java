package com.sparta.soomtut.dto.request;

import lombok.Getter;

@Getter
public class UpdatePostRequestDto {
    private String title;
    private String image;
    private String content;
    private int fee;
}
