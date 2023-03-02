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
    private int categoryId;
    private String image;
    private int fee;
    private String tutorNickname;
    private String address;

    private Long memberId;

    public LectureResponseDto(Lecture lecture) {
        this.lectureId = lecture.getId();
        this.title = lecture.getTitle();
        this.content = lecture.getContent();
        this.categoryId = lecture.getCategory().getValue();
        this.image = lecture.getImage();
        this.fee = lecture.getFee();
        this.tutorNickname = lecture.getMember().getNickname();
        this.memberId = lecture.getMember().getId();
    }

    public LectureResponseDto(Lecture lecture, Location location) {
        this.lectureId = lecture.getId();
        this.title = lecture.getTitle();
        this.content = lecture.getContent();
        this.categoryId = lecture.getCategory().getValue();
        this.image = lecture.getImage();
        this.fee = lecture.getFee();
        this.tutorNickname = lecture.getMember().getNickname();
        this.address = location.getAddress();
        this.memberId = lecture.getMember().getId();
    }


    public LectureResponseDto(Lecture lecture, String nickName, String location) {
        this.lectureId = lecture.getId();
        this.title = lecture.getTitle();
        this.content = lecture.getContent();
        this.categoryId = lecture.getCategory().getValue();

        this.image = lecture.getImage();
        this.fee = lecture.getFee();
        this.tutorNickname = nickName ;
        this.address = location;
        this.memberId = lecture.getMember().getId();
    }

    public LectureResponseDto(LectureResponseDto lectureResponse) {
        this.image = lectureResponse.image;
        this.fee = lectureResponse.fee;
        this.address = lectureResponse.getAddress();
        this.tutorNickname = lectureResponse.tutorNickname;
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
