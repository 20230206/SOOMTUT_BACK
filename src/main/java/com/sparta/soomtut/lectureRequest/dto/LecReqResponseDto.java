package com.sparta.soomtut.lectureRequest.dto;

import com.sparta.soomtut.chat.entity.ChatRoom;
import com.sparta.soomtut.lecture.entity.Lecture;
import com.sparta.soomtut.lectureRequest.entity.LectureRequest;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LecReqResponseDto {

    private Long id;
    private Long tuteeId;
    private Boolean reviewFilter;
    
    private Lecture lecture;

    @Builder(builderClassName="LectureRequestToDto", builderMethodName="toDto")
    public LecReqResponseDto(LectureRequest lectureRequest) {
        this.id = lectureRequest.getId();
        this.tuteeId = lectureRequest.getTuteeId();
        this.reviewFilter = lectureRequest.getReviewFilter();
        this.lecture = lectureRequest.getLecture();
    }
}
