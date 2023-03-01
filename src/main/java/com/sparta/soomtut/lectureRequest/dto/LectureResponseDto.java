package com.sparta.soomtut.lectureRequest.dto;

import com.sparta.soomtut.lecture.entity.Lecture;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LectureResponseDto {
    private Long lectereRequestId;
    private Lecture lecture;
    private Long tuteeId;
    private Boolean reviewFilter;


    public LectureResponseDto(Long lectereRequestId, Lecture lecture, Long tuteeId, Boolean reviewFilter) {
        this.lectereRequestId = lectereRequestId;
        this.lecture = lecture;
        this.tuteeId = tuteeId;
        this.reviewFilter = reviewFilter;
    }
}
