package com.sparta.soomtut.lectureRequest.controller;

import com.sparta.soomtut.lectureRequest.service.LectureRequestService;
import com.sparta.soomtut.util.dto.request.PageRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.sparta.soomtut.util.security.UserDetailsImpl;
import com.sparta.soomtut.util.response.ToResponse;
import com.sparta.soomtut.util.response.SuccessCode;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/lecture-request")
@RequiredArgsConstructor
public class LectureRequestController {
    
    private final LectureRequestService lectureRequestService;

    // 수업 신청
    @PostMapping("/{lectureId}")
    public ResponseEntity<?> createLectureRequest(
            @PathVariable Long lectureId,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        var data = lectureRequestService.createLectureRequest(lectureId, userDetails.getMemberId());
        return ToResponse.of(data, SuccessCode.LECTUREREQUEST_CREATE_OK);
    }

    // 수업 확정
    @PostMapping("/{lectureRequestId}/accept")
    public ResponseEntity<?> acceptLecture(
        @PathVariable Long lectureRequestId,
        @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        var data = lectureRequestService.acceptLecture(lectureRequestId, userDetails.getMember());
        return ToResponse.of(data, SuccessCode.LECTUREREQUEST_ACCEPT_OK);
    }

    // 수업 완료
    @PostMapping("/{lectureRequestId}/complete")
    public ResponseEntity<?> complete(
        @PathVariable Long lectureRequestId,
        @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        var data = lectureRequestService.completeLecture(lectureRequestId, userDetails.getMember());
        return ToResponse.of(data, SuccessCode.LECTUREREQUEST_COMPLETE_OK);
    }

    // 수업 신청 중 완료가 안된 신청이 있는지 조회
    @GetMapping("/{lectureId}/existsLectureRequest") 
    public ResponseEntity<?> existsLectureRequest(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long lectureId)
    {
        var data = lectureRequestService.existsLectureRequestByStateIsNotComplete(userDetails.getMemberId(), lectureId);
        return ToResponse.of(data, SuccessCode.LECTUREREQUEST_ISEXISTS_OK);
    }

    @GetMapping("/{lectureId}") 
    public ResponseEntity<?> getLectureRequest(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long lectureId)
    {
        var data = lectureRequestService.getLectureRequestByStateIsNotComplete(userDetails.getMemberId(), lectureId);
        return ToResponse.of(data, SuccessCode.LECTUREREQUEST_GET_OK);
    }

    // 완료된 수업 목록 조회
    @GetMapping("/done")
    public ResponseEntity<?> getCompleteLecture(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @ModelAttribute PageRequestDto pageable)
    {
        var data = lectureRequestService.getCompleteLecture(userDetails.getMemberId(),pageable.toPageable());
        return ToResponse.of(data, SuccessCode.LECTURE_GETDONELECTURES_OK);
    }

    // 완료된 수업중 리뷰작성이 안된 수업조회
    @GetMapping("/reviewFilter")
    public ResponseEntity<?> reviewFilter(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @ModelAttribute PageRequestDto pageable)
    {
        var data = lectureRequestService.reviewFilter(userDetails.getMemberId(),pageable.toPageable());
        return ToResponse.of(data, SuccessCode.LECTURE_GETLECTURE_OK);
    }

}
