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

    public LectureResponseDto(Lecture post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.categoryId = post.getCategory().getValue();
        this.image = post.getImage();
        this.fee = post.getFee();
        this.tutorNickname = post.getMember().getNickname();
    }

    public LectureResponseDto(Lecture post, Location location) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.categoryId = post.getCategory().getValue();
        this.image = post.getImage();
        this.fee = post.getFee();
        this.tutorNickname = post.getMember().getNickname();
        this.location = location.getAddress();
    }


    public LectureResponseDto(Lecture post, String nickName, String location) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.categoryId = post.getCategory().getValue();

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
