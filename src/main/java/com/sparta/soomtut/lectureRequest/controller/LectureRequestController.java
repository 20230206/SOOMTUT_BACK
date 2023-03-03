package com.sparta.soomtut.lectureRequest.controller;

import com.sparta.soomtut.lectureRequest.dto.LecReqResponseDto;
import com.sparta.soomtut.lectureRequest.service.LectureRequestService;
import com.sparta.soomtut.util.dto.request.PageRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.sparta.soomtut.util.security.UserDetailsImpl;
import com.sparta.soomtut.util.response.ToResponse;
import com.sparta.soomtut.util.response.SuccessCode;

import com.sparta.soomtut.lecture.service.LectureService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/lecture-request")
@RequiredArgsConstructor
public class LectureRequestController {
    
    private final LectureRequestService lectureRequestService;
    private final LectureService lectureService;

    // 수업 신청
    @PostMapping("/{lectureid}")
    public ResponseEntity<?> createLectureRequest(
            @PathVariable Long lectureid,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        var data = lectureRequestService.createLectureRequest(lectureid, userDetails.getMemberId());
        return ToResponse.of(data, SuccessCode.LECTUREREQUEST_CREATE_OK);
    }

    // 수업 확정
    @PostMapping("/{lecturerequestid}/accept")
    public ResponseEntity<?> acceptLecture(
        @PathVariable Long lecturerequestid,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        var data = lectureRequestService.acceptLecture(lecturerequestid, userDetails.getMember());
        return ToResponse.of(data, SuccessCode.LECTUREREQUEST_ACCEPT_OK);
    }

    // 수업 완료
    @PostMapping("/{lecturerequestid}/complete")
    public ResponseEntity<?> complete(
        @PathVariable Long lecturerequestid,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        var data = lectureRequestService.completeLecture(lecturerequestid, userDetails.getMember());
        return ToResponse.of(data, SuccessCode.LECTUREREQUEST_COMPLETE_OK);
    }

    // 수업 신청 중 완료가 안된 신청이 있는지 조회
    @GetMapping("/{lectureId}/existsLectureRequest") 
    public ResponseEntity<?> existsLectureRequest(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long lectureId
    )
    {
        var data = lectureRequestService.existsLectureRequestByStateIsNotComplete(userDetails.getMemberId(), lectureId);
        return ToResponse.of(data, SuccessCode.LECTUREREQUEST_ISEXISTS_OK);
    }

    @GetMapping("/{lectureId}") 
    public ResponseEntity<?> getLectureRequest(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long lectureId
    )
    {
        var data = lectureRequestService.getLectureRequestByStateIsNotComplete(userDetails.getMemberId(), lectureId);
        return ToResponse.of(data, SuccessCode.LECTUREREQUEST_GET_OK);
    }

    // TODO: 제 생각에, 아래 3개 메서드는 Lecture 로 가야하는 게 맞는 것 같습니다. 그리고 서비스단은 boardService 로!
    // 수업 신청 목록 조회
    @GetMapping
    public ResponseEntity<?> getLecturesRequests(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @ModelAttribute PageRequestDto pageable
    )
    {
        return ToResponse.of(null, SuccessCode.LECTUREREQUEST_GETREQUESTS_OK);
    }

    // 완료된 수업 목록 조회
    // TODO: 수업의 완료라기 보다는 수업 신청의 완료라고 보는 것이 타당한 것 같습니다.
    @GetMapping("/done")
    public ResponseEntity<?> getCompleteLecture(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @ModelAttribute PageRequestDto pageable
    ) {
        var data = lectureRequestService.getCompleteLecture(userDetails.getMemberId(),pageable.toPageable());
        return ToResponse.of(data, SuccessCode.LECTURE_GETDONELECUTES_OK);
    }

    // 완료된 수업중 리뷰작성이 안된 수업조회
    @GetMapping("/reviewfilter")
    public ResponseEntity<?> reviewFilter(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @ModelAttribute PageRequestDto pageable
    ) {
        var data = lectureRequestService.reviewFilter(userDetails.getMemberId(),pageable.toPageable());
        return ToResponse.of(data, SuccessCode.LECTURE_GETLECTURE_OK);
    }

}
