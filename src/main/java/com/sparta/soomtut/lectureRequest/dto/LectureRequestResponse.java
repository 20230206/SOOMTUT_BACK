package com.sparta.soomtut.lectureRequest.dto;

import com.sparta.soomtut.lecture.entity.Lecture;
import com.sparta.soomtut.lectureRequest.entity.LectureRequest;
import com.sparta.soomtut.lectureRequest.entity.LectureState;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LectureRequestResponse {

    private Long id;
    private Long tuteeId;
    private Boolean reviewed;
    private LectureState state;
    
    private Lecture lecture;

    @Builder(builderClassName="LectureRequestToDto", builderMethodName="toDto")
    public LectureRequestResponse(LectureRequest lectureRequest) {
        this.id = lectureRequest.getId();
        this.state = lectureRequest.getLectureState();
        this.tuteeId = lectureRequest.getTuteeId();
        this.reviewed = lectureRequest.getReviewed();
        this.lecture = lectureRequest.getLecture();
    }
}
