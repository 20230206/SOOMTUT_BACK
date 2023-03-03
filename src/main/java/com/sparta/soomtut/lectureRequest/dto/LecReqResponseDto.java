package com.sparta.soomtut.lectureRequest.dto;

import com.sparta.soomtut.lecture.entity.Lecture;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LecReqResponseDto {

    private Long lectureRequestId;
    private Lecture lecture;
    private Long tuteeId;
    private Boolean reviewFilter;

    private LecReqResponseDto(
            Long lectureRequestId,
            Lecture lecture,
            Long tuteeId,
            Boolean reviewFilter)
    {
        this.lectureRequestId = lectureRequestId;
        this.lecture = lecture;
        this.tuteeId = tuteeId;
        this.reviewFilter = reviewFilter;
    }

    public static LecReqResponseDto of(
            Long lectureRequestId,
            Lecture lecture,
            Long tuteeId,
            Boolean reviewFilter)
    {
        return new LecReqResponseDto(
                lectureRequestId,
                lecture,
                tuteeId,
                reviewFilter);
    }

}
