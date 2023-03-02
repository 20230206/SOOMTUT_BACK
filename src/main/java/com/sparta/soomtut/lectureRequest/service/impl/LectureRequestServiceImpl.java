package com.sparta.soomtut.lectureRequest.service.impl;

import com.sparta.soomtut.lecture.dto.response.LectureResponseDto;
import com.sparta.soomtut.lecture.entity.Lecture;
import com.sparta.soomtut.lecture.repository.LectureRepository;
import com.sparta.soomtut.lecture.service.LectureService;
import com.sparta.soomtut.lectureRequest.dto.LecReqResponseDto;
import com.sparta.soomtut.lectureRequest.entity.LectureRequest;
import com.sparta.soomtut.lectureRequest.repository.LectureRequestRepository;
import com.sparta.soomtut.lectureRequest.service.LectureRequestService;
import com.sparta.soomtut.location.service.LocationService;
import com.sparta.soomtut.member.entity.Member;
import com.sparta.soomtut.util.enums.LectureState;
import com.sparta.soomtut.util.response.ErrorCode;
import com.sparta.soomtut.util.exception.CustomException;
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

    private final LocationService locationService;

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

    // TODO: 성공 메시지로 바꿔주세요. 그리고 Member 객체 안 쓰시면 안 넘겨줘도 됩니다. 컨트롤러에서만 받으시면 됩니다.

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
    public Page<LectureResponseDto> getCompleteLecture(Long memberId, Pageable pageable) {
        Page<LectureRequest> lectureRequestPage = getAllByTuteeIdByAndStateIsDone(memberId, pageable);
        Page<Lecture> lectureList = lectureRequestPage.map(lectureRequest -> lectureRequest.getLecture());
        return lectureList.map(item->new LectureResponseDto(item, locationService.findMemberLocation(memberId)));
    }

    // 완료된 수업중 리뷰가 없는 수업목록 조회
    @Override
    @Transactional
    public Page<LectureResponseDto> reviewFilter(Long memberId, Pageable pageable) {
        Page<LectureRequest> lectureRequestPage = getAllByTuteeIdByAndStateIsDoneAndFalse(memberId, pageable);
        Page<Lecture> lectureList = lectureRequestPage.map(lectureRequest -> lectureRequest.getLecture());
        return lectureList.map(item->new LectureResponseDto(item, locationService.findMemberLocation(memberId)));
    }



    // 레파지토리 접근 함수.
    @Override
    public LectureRequest getLectureRequestById(Long lectureRequestId) {
        return lectureRequestRepository.findById(lectureRequestId).orElseThrow(
                ()->new IllegalArgumentException(ErrorCode.NOT_FOUND_LECTURE_REQUEST.getMessage())
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
        if(existsLectureRequestByMemberIdAndLectureId(memberId, lectureId, LectureState.NONE) ||
        existsLectureRequestByMemberIdAndLectureId(memberId, lectureId, LectureState.IN_PROGRESS))
            return true;

        return false;
    }

    private boolean existsLectureRequestByMemberIdAndLectureId(Long memberId, Long lectureId, LectureState lectureState) {
        return lectureRequestRepository.existsByTuteeIdAndLectureIdAndLectureState(memberId, lectureId, lectureState);
    }

    @Override
    @Transactional(readOnly=true)
    public LecReqResponseDto getLectureRequestByStateIsNotComplete(Long memberId, Long lectureId) {
        LectureRequest lecreq = getLectureRequestByTuteeIdAndLectureIdAndLectureState(memberId, lectureId);
        return LecReqResponseDto.of(lecreq.getId(), lecreq.getLecture(), lecreq.getTuteeId(), lecreq.getReviewFilter());
    }

    private LectureRequest getLectureRequestByTuteeIdAndLectureIdAndLectureState(Long memberId, Long lectureId) {
        return lectureRequestRepository.findByTuteeIdAndLectureIdAndLectureStateIsNotDone(memberId, lectureId)
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_LECTURE_REQUEST));
    }
}
