package com.sparta.soomtut.lectureRequest.service.impl;

import com.sparta.soomtut.lecture.entity.Lecture;
import com.sparta.soomtut.lecture.repository.LectureRepository;
import com.sparta.soomtut.lecture.service.LectureService;
import com.sparta.soomtut.lectureRequest.dto.LectureResponseDto;
import com.sparta.soomtut.lectureRequest.entity.LectureRequest;
import com.sparta.soomtut.lectureRequest.repository.LectureRequestRepository;
import com.sparta.soomtut.lectureRequest.service.LectureRequestService;
import com.sparta.soomtut.member.entity.Member;
import com.sparta.soomtut.util.enums.LectureState;
import com.sparta.soomtut.util.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectureRequestServiceImpl implements LectureRequestService{

    private final LectureRequestRepository lectureRequestRepository;
    private final LectureRepository lectureRepository;

    // 수업 신청
    @Override
    @Transactional
    public LectureResponseDto createLectureRequest(Long lectureId, Long memberId) {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(
                () -> new IllegalArgumentException(ErrorCode.NOT_FOUND_POST.getMessage())
        );

//        boolean isExistsRequest = tuitionRequestRepository.existsByPostIdAndTuteeIdAndTuitionState(lectureid, member.getId(), LectureState.NONE);
//        if(isExistsRequest) return "수업 신청이 완료되었습니다.";

        LectureRequest lectureRequest = new LectureRequest(lecture, memberId);

        lectureRequestRepository.save(lectureRequest);
        return new LectureResponseDto(lectureRequest.getLectureId(), lectureRequest.getLecture(), lectureRequest.getTuteeId(), lectureRequest.getReviewFilter());
    }

    @Override
    @Transactional
    public String lectureConfirmed(Long lectureRequestId, Member member) {
        LectureRequest lectureRequest = lectureRequestRepository.findById(lectureRequestId).orElseThrow(
                () -> new IllegalArgumentException(ErrorCode.NOT_FOUND_REQUEST.getMessage())
        );
        lectureRequest.changeConfirmed();
        return "";
    }

    @Override
    @Transactional
    public String lectureComplete(Long lectureRequestId, Member member) {
        LectureRequest lectureRequest = lectureRequestRepository.findById(lectureRequestId).orElseThrow(
                () -> new IllegalArgumentException(ErrorCode.NOT_FOUND_REQUEST.getMessage())
        );

        lectureRequest.changeComplete();
        return "";
    }


    // 완료한 수업 목록 조회
    @Override
    @Transactional
    public List<Lecture> getCompleteLecture(Member member) {
        List<LectureRequest> lectureRequestList = lectureRequestRepository.findAllByTuteeIdAndLectureState(member.getId(), LectureState.DONE);
        List<Lecture> lectureList = lectureRequestList.stream().map((item) -> item.getLecture()).collect(Collectors.toList());
        System.out.println(lectureRequestList);
        return lectureList;
    }

    // 완료된 수업중 리뷰가 없는 수업목록 조회
    @Override
    @Transactional
    public List<Lecture> reviewFilter(Member member) {
        List<LectureRequest> lectureRequestList = lectureRequestRepository.findAllByTuteeIdAndLectureStateAndReviewFilterIsFalse(member.getId(), LectureState.DONE);
        List<Lecture> lectureList = lectureRequestList.stream().map((item) -> item.getLecture()).collect(Collectors.toList());
        return lectureList;
    }

    @Override
    public LectureRequest getLectureRequestById(Long lectureRequestId) {
        return lectureRequestRepository.findById(lectureRequestId).orElseThrow(
                // 에러 메시지 임시로 지정해둠. 새로 만들어서 바꿔야함.
                ()->new IllegalArgumentException(ErrorCode.NOT_FOUND_CLASS.getMessage())
        );
    }
}
