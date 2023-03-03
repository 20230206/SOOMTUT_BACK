package com.sparta.soomtut.lectureRequest.service;

import com.sparta.soomtut.lecture.dto.response.LectureResponseDto;
import com.sparta.soomtut.lectureRequest.dto.LecReqResponseDto;
import com.sparta.soomtut.lectureRequest.entity.LectureRequest;
import com.sparta.soomtut.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LectureRequestService {

    LecReqResponseDto createLectureRequest(Long lectureid, Long memberId);
    LecReqResponseDto acceptLecture(Long lectureId, Member member);
    LecReqResponseDto completeLecture(Long lectureId, Member member);
    Page<LectureResponseDto> getCompleteLecture(Long memberId, Pageable pageable);
    Page<LectureResponseDto> reviewFilter(Long memberId, Pageable pageable);
    LectureRequest getLectureRequestById(Long lectureRequestId);
    Page<LectureRequest> getAllByTuteeIdByAndStateIsDoneAndFalse(Long tuteeId, Pageable pageable);
    Page<LectureRequest> getAllByTuteeIdByAndStateIsDone(Long tuteeId, Pageable pageable);
    boolean existsLectureRequestByStateIsNotComplete(Long memberId, Long lectureId);
    LecReqResponseDto getLectureRequestByStateIsNotComplete(Long memberId, Long lectureId);

}
