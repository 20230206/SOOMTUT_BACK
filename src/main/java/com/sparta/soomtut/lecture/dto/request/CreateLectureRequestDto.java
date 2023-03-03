package com.sparta.soomtut.lecture.dto.request;

import lombok.Getter;

@Getter
public class CreateLectureRequestDto {
    private String title;
    private String imgPath;
    private String content;
    private int category;
    private int fee;

}
