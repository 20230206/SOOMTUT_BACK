package com.sparta.soomtut.lecture.dto.response;

import com.sparta.soomtut.lecture.entity.Lecture;
import com.sparta.soomtut.location.entity.Location;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LectureResponseDto {
    private Long lectureId;
    private String title;
    private String content;
    private Long categoryId;
    private String image;
    private int fee;
    private String tutorNickname;
    private String address;

    public LectureResponseDto(Lecture lecture) {
        this.lectureId = lecture.getId();
        this.title = lecture.getTitle();
        this.content = lecture.getContent();
        this.categoryId = lecture.getCategoryId();
        this.image = lecture.getImage();
        this.fee = lecture.getFee();
        this.tutorNickname = lecture.getTutorNickname();
    }

    public LectureResponseDto(Lecture lecture, Location location) {
        this.lectureId = lecture.getId();
        this.title = lecture.getTitle();
        this.content = lecture.getContent();
        this.categoryId = lecture.getCategoryId();
        this.image = lecture.getImage();
        this.fee = lecture.getFee();
        this.tutorNickname =lecture.getTutorNickname();
        this.address = location.getAddress();
    }


    public LectureResponseDto(Lecture lecture, String nickName, Location location) {
        this.lectureId = lecture.getId();
        this.title = lecture.getTitle();
        this.content = lecture.getContent();
        this.categoryId = lecture.getCategoryId();

        this.image = lecture.getImage();
        this.fee = lecture.getFee();
        this.tutorNickname = nickName ;
        this.address = location.getAddress();
    }

    public LectureResponseDto(LectureResponseDto postResponseDto) {
        this.image = postResponseDto.image;
        this.fee = postResponseDto.fee;
        this.address = postResponseDto.getAddress();
        this.tutorNickname = postResponseDto.tutorNickname;
    }

    public LectureResponseDto(String image,int fee, String address,String tutorNickname,String content,String title ) {
        this.image = image;
        this.fee = fee;
        this.address = address;
        this.tutorNickname = tutorNickname;
        this.content = content;
        this.title = title;
    }
}
