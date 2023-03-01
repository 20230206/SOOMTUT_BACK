package com.sparta.soomtut.lectureRequest.service;

import com.sparta.soomtut.lecture.entity.Lecture;
import com.sparta.soomtut.lectureRequest.dto.LectureResponseDto;
import com.sparta.soomtut.lectureRequest.entity.LectureRequest;
import com.sparta.soomtut.member.entity.Member;

import java.util.List;
import java.util.Optional;

public interface LectureRequestService {

    LectureResponseDto createLectureRequest(Long lectureid, Long memberId);
    String lectureConfirmed(Long lectureId, Member member);
    String lectureComplete(Long lectureId, Member member);
    List<Lecture> getCompleteLecture(Member member);
    List<Lecture> reviewFilter(Member member);
    LectureRequest getLectureRequestById(Long lectureRequestId);
}
