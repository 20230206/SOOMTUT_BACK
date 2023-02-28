package com.sparta.soomtut.lectureRequest.service;

import com.sparta.soomtut.lecture.service.LectureService;
import com.sparta.soomtut.lectureRequest.entity.LectureRequest;
import com.sparta.soomtut.lectureRequest.repository.LectureRequestRepository;
import com.sparta.soomtut.member.entity.Member;
import com.sparta.soomtut.util.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LectureRequestServiceImpl implements LectureRequestService{

    private final LectureRequestRepository lectureRequestRepository;
    private final LectureService lectureService;

    @Override
    @Transactional
    public String lectureConfirmed(Long lectureRequestId, Member member) {
        LectureRequest lectureRequest = lectureRequestRepository.findById(lectureRequestId).orElseThrow(
                () -> new IllegalArgumentException("ConfirmedError")
        );
        lectureRequest.changeConfirmed();
        return "수업이 확정되었습니다.";

    }

    @Override
    @Transactional
    public String lectureComplete(Long lectureRequestId, Member member) {
        LectureRequest lectureRequest = lectureRequestRepository.findById(lectureRequestId).orElseThrow(
                () -> new IllegalArgumentException("Error")
        );

        lectureRequest.changeComplete();
        return "수업이 완료되었습니다.";
    }

    @Override
    public LectureRequest getLectureRequestById(Long lectureRequestId) {
        return lectureRequestRepository.findById(lectureRequestId).orElseThrow(
                // 에러 메시지 임시로 지정해둠. 새로 만들어서 바꿔야함.
                ()->new IllegalArgumentException(ErrorCode.NOT_FOUND_CLASS.getMessage())
        );
    }
}
