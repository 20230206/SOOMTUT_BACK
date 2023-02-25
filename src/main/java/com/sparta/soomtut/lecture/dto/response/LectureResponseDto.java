package com.sparta.soomtut.lecture.dto.response;

import com.sparta.soomtut.lecture.entity.Lecture;
import com.sparta.soomtut.location.entity.Location;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LectureResponseDto {
    private Long postId;
    private String title;
    private String content;
    private Long categoryId;
    private String image;
    private int fee;
    private String tutorNickname;
    private String location;

    public LectureResponseDto(Lecture post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.categoryId = post.getCategoryId();
        this.image = post.getImage();
        this.fee = post.getFee();
        this.tutorNickname = post.getMember().getNickname();
    }

    public LectureResponseDto(Lecture post, Location location) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.categoryId = post.getCategoryId();
        this.image = post.getImage();
        this.fee = post.getFee();
        this.tutorNickname = post.getMember().getNickname();
        this.location = location.getAddress();
    }


    public LectureResponseDto(Lecture post, String nickName, String location) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.categoryId = post.getCategoryId();

        this.image = post.getImage();
        this.fee = post.getFee();
        this.tutorNickname = nickName ;
        this.location = location;
    }

    public LectureResponseDto(LectureResponseDto postResponseDto) {
        this.image = postResponseDto.image;
        this.fee = postResponseDto.fee;
        this.location = postResponseDto.location;
        this.tutorNickname = postResponseDto.tutorNickname;
    }

    public LectureResponseDto(String image,int fee, String location,String tutorNickname,String content,String title ) {
        this.image = image;
        this.fee = fee;
        this.location = location;
        this.tutorNickname = tutorNickname;
        this.content = content;
        this.title = title;
    }
}
