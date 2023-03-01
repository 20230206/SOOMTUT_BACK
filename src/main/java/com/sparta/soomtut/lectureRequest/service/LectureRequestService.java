package com.sparta.soomtut.lectureRequest.service;

import com.sparta.soomtut.lecture.entity.Lecture;
import com.sparta.soomtut.lectureRequest.dto.LecReqResponseDto;
import com.sparta.soomtut.lectureRequest.entity.LectureRequest;
import com.sparta.soomtut.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface LectureRequestService {

    LecReqResponseDto createLectureRequest(Long lectureid, Long memberId);
    String lectureConfirmed(Long lectureId, Member member);
    String lectureComplete(Long lectureId, Member member);
    Page<Lecture> getCompleteLecture(Long memberId, Pageable pageable);
    Page<Lecture> reviewFilter(Long memberId, Pageable pageable);
    LectureRequest getLectureRequestById(Long lectureRequestId);

    Page<LectureRequest> getAllByTuteeIdByAndStateIsDoneAndFalse(Long tuteeId);

    Page<LectureRequest> getAllByTuteeIdByAndStateIsDone(Long tuteeId);
}
