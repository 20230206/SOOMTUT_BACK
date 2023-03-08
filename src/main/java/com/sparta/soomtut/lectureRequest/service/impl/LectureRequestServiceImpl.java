package com.sparta.soomtut.lectureRequest.service.impl;

import com.sparta.soomtut.lecture.dto.response.LectureResponseDto;
import com.sparta.soomtut.lecture.entity.Lecture;
import com.sparta.soomtut.lecture.service.LectureService;
import com.sparta.soomtut.lectureRequest.dto.LectureRequestResponse;
import com.sparta.soomtut.lectureRequest.entity.LectureRequest;
import com.sparta.soomtut.lectureRequest.entity.LectureState;
import com.sparta.soomtut.lectureRequest.repository.LectureRequestRepository;
import com.sparta.soomtut.lectureRequest.service.LectureRequestService;
import com.sparta.soomtut.member.entity.Member;
import com.sparta.soomtut.util.response.ErrorCode;
import com.sparta.soomtut.util.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LectureRequestServiceImpl implements LectureRequestService{

    private final LectureRequestRepository lectureRequestRepository;
    private final LectureService lectureService;

    // 수업 신청
    @Override
    @Transactional
    public LectureRequestResponse createLectureRequest(Long lectureId, Long memberId) {
        Lecture lecture = lectureService.getLectureById(lectureId);
        if(lecture.getMember().getId().equals(memberId)) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        if(this.existsLectureRequestByStateIsNotComplete(memberId, lectureId)) {
            throw new CustomException(ErrorCode.NOT_FOUND_REQUEST);
        }

        LectureRequest lectureRequest = new LectureRequest(lecture, memberId);
        lectureRequestRepository.save(lectureRequest);
        return LectureRequestResponse.toDto().lectureRequest(lectureRequest).build();
    }

    @Override
    @Transactional
    public LectureRequestResponse acceptLecture(Long lectureRequestId, Member member) {
        LectureRequest lectureRequest = getLectureRequestById(lectureRequestId);
        lectureRequest.changeConfirmed();
        return LectureRequestResponse.toDto().lectureRequest(lectureRequest).build();
    }

    @Override
    @Transactional
    public LectureRequestResponse completeLecture(Long lectureRequestId, Member member) {
        LectureRequest lectureRequest = getLectureRequestById(lectureRequestId);
        lectureRequest.changeComplete();
        return LectureRequestResponse.toDto().lectureRequest(lectureRequest).build();
    }

    // 완료한 수업 목록 조회
    @Override
    @Transactional
    public Page<LectureResponseDto> getCompleteLecture(Long memberId, Pageable pageable) {
        Page<LectureRequest> lectureRequestPage = getAllByTuteeIdByAndStateIsDone(memberId, pageable);
        Page<Lecture> lectureList = lectureRequestPage.map(LectureRequest::getLecture);
        return lectureList.map(item -> LectureResponseDto.toDto().lecture(item).build());
    }

    // 완료된 수업중 리뷰가 없는 수업목록 조회
    @Override
    @Transactional
    public Page<LectureResponseDto> reviewFilter(Long memberId, Pageable pageable) {
        Page<LectureRequest> lectureRequestPage = getAllByTuteeIdByAndStateIsDoneAndFalse(memberId, pageable);
        Page<Lecture> lectureList = lectureRequestPage.map(LectureRequest::getLecture);
        return lectureList.map(item -> LectureResponseDto.toDto().lecture(item).build());
    }

    // 레파지토리 접근 함수.
    @Override
    public LectureRequest getLectureRequestById(Long lectureRequestId) {
        return lectureRequestRepository.findById(lectureRequestId).orElseThrow(
                ()-> new IllegalArgumentException(ErrorCode.NOT_FOUND_LECTURE_REQUEST.getMessage())
        );
    }

    @Override
    public Page<LectureRequest> getAllByTuteeIdByAndStateIsDoneAndFalse(Long tuteeId, Pageable pageable) {
        return lectureRequestRepository.findAllByTuteeIdByAndStateIsDoneAndFalse(tuteeId, pageable);
    }

    @Override
    public Page<LectureRequest> getAllByTuteeIdByAndStateIsDone(Long tuteeId, Pageable pageable) {
        return lectureRequestRepository.findAllByTuteeIdByAndStateIsDone(tuteeId, pageable);
    }

    @Override
    @Transactional(readOnly=true)
    public boolean existsLectureRequestByStateIsNotComplete(Long memberId, Long lectureId) {
        return existsLectureRequestByMemberIdAndLectureId(memberId, lectureId, LectureState.NONE) ||
                existsLectureRequestByMemberIdAndLectureId(memberId, lectureId, LectureState.IN_PROGRESS);
    }

    private boolean existsLectureRequestByMemberIdAndLectureId(Long memberId, Long lectureId, LectureState lectureState) {
        return lectureRequestRepository.existsByTuteeIdAndLectureIdAndLectureState(memberId, lectureId, lectureState);
    }

    @Override
    @Transactional(readOnly=true)
    public LectureRequestResponse getLectureRequestByStateIsNotComplete(Long memberId, Long lectureId) {
        LectureRequest lectureRequest = getLectureRequestByTuteeIdAndLectureIdAndLectureState(memberId, lectureId);
        return LectureRequestResponse.toDto().lectureRequest(lectureRequest).build();
    }

    private LectureRequest getLectureRequestByTuteeIdAndLectureIdAndLectureState(Long memberId, Long lectureId) {
        return lectureRequestRepository.findByTuteeIdAndLectureIdAndLectureStateIsNotDone(memberId, lectureId)
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_LECTURE_REQUEST));
    }
}
