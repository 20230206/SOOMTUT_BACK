package com.sparta.soomtut.lecture.dto.request;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private String title;
    private String image;
    private String content;
    private Long category;
    private int fee;
}