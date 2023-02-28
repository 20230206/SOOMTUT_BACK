package com.sparta.soomtut.lectureRequest.service;

import com.sparta.soomtut.lectureRequest.entity.LectureRequest;
import com.sparta.soomtut.member.entity.Member;

import java.util.Optional;

public interface LectureRequestService {

    String lectureConfirmed(Long lectureId, Member member);
    String lectureComplete(Long lectureId, Member member);

    LectureRequest getLectureRequestById(Long lectureRequestId);
}
