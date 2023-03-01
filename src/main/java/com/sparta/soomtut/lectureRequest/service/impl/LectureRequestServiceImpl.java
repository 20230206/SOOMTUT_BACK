package com.sparta.soomtut.lectureRequest.service.impl;

import com.sparta.soomtut.lecture.entity.Lecture;
import com.sparta.soomtut.lecture.repository.LectureRepository;
import com.sparta.soomtut.lecture.service.LectureService;
import com.sparta.soomtut.lectureRequest.dto.LecReqResponseDto;
import com.sparta.soomtut.lectureRequest.entity.LectureRequest;
import com.sparta.soomtut.lectureRequest.repository.LectureRequestRepository;
import com.sparta.soomtut.lectureRequest.service.LectureRequestService;
import com.sparta.soomtut.member.entity.Member;
import com.sparta.soomtut.util.enums.LectureState;
import com.sparta.soomtut.util.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectureRequestServiceImpl implements LectureRequestService{

    private final LectureRequestRepository lectureRequestRepository;
    private final LectureService lectureService;

    // 수업 신청
    @Override
    @Transactional
    public LecReqResponseDto createLectureRequest(Long lectureId, Long memberId) {
        Lecture lecture = lectureService.getLectureById(lectureId);

        if(lectureRequestRepository.existsByTuteeIdAndLectureId(memberId, lectureId)){
            // 에러메시지 수정 필요.
           new IllegalArgumentException(ErrorCode.NOT_FOUND_REQUEST.getMessage());
        }

        LectureRequest lectureRequest = new LectureRequest(lecture, memberId);
        lectureRequestRepository.save(lectureRequest);

        return LecReqResponseDto.of(lectureRequest.getLectureId(),
                lectureRequest.getLecture(),
                lectureRequest.getTuteeId(),
                lectureRequest.getReviewFilter()
        );
    }

    @Override
    @Transactional
    public String lectureConfirmed(Long lectureRequestId, Member member) {
        LectureRequest lectureRequest = getLectureRequestById(lectureRequestId);
        lectureRequest.changeConfirmed();
        return "";
    }

    @Override
    @Transactional
    public String lectureComplete(Long lectureRequestId, Member member) {
        LectureRequest lectureRequest = getLectureRequestById(lectureRequestId);

        lectureRequest.changeComplete();
        return "";
    }

    // TODO: 객체 그 자체를 반환해주기보다 DTO 를 반환해주는 것이 좋습니다.
    // 이유는 제가 노션 3월2일 페이지에 정리해두었습니다.
    // 목록 조회는 페이징이 필요합니다.

    // 완료한 수업 목록 조회
    @Override
    @Transactional
    public Page<Lecture> getCompleteLecture(Long memberId, Pageable pageable) {
        //List<Lecture> lectureList = lectureRequestList.stream().map((item) -> item.getLecture()).collect(Collectors.toList());
        Page<LectureRequest> lectureRequestPage = getAllByTuteeIdByAndStateIsDone(memberId);
        Page<Lecture> lectureList = lectureRequestPage.map(lectureRequest -> lectureRequest.getLecture());

        return lectureList;
    }

    // 완료된 수업중 리뷰가 없는 수업목록 조회
    @Override
    @Transactional
    public Page<Lecture> reviewFilter(Long memberId, Pageable pageable) {
        Page<LectureRequest> lectureRequestPage = getAllByTuteeIdByAndStateIsDoneAndFalse(memberId);
        Page<Lecture> lectureList = lectureRequestPage.map(lectureRequest -> lectureRequest.getLecture());
        return lectureList;
    }

    @Override
    public LectureRequest getLectureRequestById(Long lectureRequestId) {
        return lectureRequestRepository.findById(lectureRequestId).orElseThrow(
                ()->new IllegalArgumentException(ErrorCode.NOT_FOUND_LECTURE_REQUEST.getMessage())
        );
    }

    @Override
    public Page<LectureRequest> getAllByTuteeIdByAndStateIsDoneAndFalse(Long tuteeId) {
        return lectureRequestRepository.findAllByTuteeIdByAndStateIsDoneAndFalse(tuteeId);
    }

    @Override
    public Page<LectureRequest> getAllByTuteeIdByAndStateIsDone(Long tuteeId) {
        return lectureRequestRepository.findAllByTuteeIdByAndStateIsDone(tuteeId);
    }


}
