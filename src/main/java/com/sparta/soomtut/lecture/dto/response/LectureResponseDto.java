package com.sparta.soomtut.lecture.dto.response;

import com.sparta.soomtut.lecture.entity.Lecture;
import com.sparta.soomtut.member.entity.Member;

import com.sparta.soomtut.member.dto.response.MemberInfoResponse;

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
    private String content;
    private int favorit;
    private int fee;

    private MemberInfoResponse member;

    @Builder(builderClassName="LectureResponseToDto", builderMethodName="toDto")
    public LectureResponseDto(Lecture lecture) {
        this.id = lecture.getId();
        this.image = lecture.getImage();
        this.title = lecture.getTitle();
        this.categoryId = lecture.getCategory().getValue();
        this.content = lecture.getContent();
        this.favorit = lecture.getFavorite();
        this.fee = lecture.getFee();

        this.member = MemberInfoResponse.toDto().member(lecture.getMember()).build();
    }
}
