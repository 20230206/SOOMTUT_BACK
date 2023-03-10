package com.sparta.soomtut.lecture.dto.response;

import com.sparta.soomtut.lecture.entity.Lecture;

import com.sparta.soomtut.member.dto.response.MemberResponse;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LectureResponseDto {

    private Long id;
    private String image;
    private String title;
    private int categoryId;
    private String categoryName;
    private String content;
    private int favorit;
    private int fee;

    private MemberResponse member;

    @Builder(builderClassName="LectureResponseToDto", builderMethodName="toDto")
    public LectureResponseDto(Lecture lecture) {
        this.id = lecture.getId();
        this.image = lecture.getImage();
        this.title = lecture.getTitle();
        this.categoryId = lecture.getCategory().getValue();
        this.categoryName = lecture.getCategory().getName();
        this.content = lecture.getContent();
        this.favorit = lecture.getFavorite();
        this.fee = lecture.getFee();

        this.member = MemberResponse.toDto().member(lecture.getMember()).build();
    }
}
