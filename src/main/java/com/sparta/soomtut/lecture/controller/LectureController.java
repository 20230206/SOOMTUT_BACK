package com.sparta.soomtut.lecture.controller;

import com.sparta.soomtut.lecture.dto.request.CreateLectureRequestDto;
import com.sparta.soomtut.lecture.dto.request.UpdateLectureRequestDto;
import com.sparta.soomtut.lecture.dto.response.LectureResponseDto;
import com.sparta.soomtut.lecture.service.BookmarkService;
import com.sparta.soomtut.lecture.service.LectureService;
import com.sparta.soomtut.member.entity.Member;
import com.sparta.soomtut.util.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.sparta.soomtut.lecture.service.impl.BoardServiceImpl;
import com.sparta.soomtut.util.dto.request.PageRequestDto;
import com.sparta.soomtut.util.response.SuccessCode;
import com.sparta.soomtut.util.response.ToResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lecture")
public class LectureController {
    private final LectureService lectureService;
    private final BoardServiceImpl boardService;
    private final BookmarkService favMemberPostService;
    
    // 수업 등록
    @PostMapping
    public ResponseEntity<?> createLecture(
        @RequestBody CreateLectureRequestDto postRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        var data = lectureService.createLecture(userDetails.getMember(), postRequestDto);
        return ToResponse.of(data, SuccessCode.LECTURE_CREATE_OK);
    }

    // 수업 수정
    @PutMapping("/{lectureid}")
    public ResponseEntity<?> updateLecture(
        @PathVariable Long lectureid, 
        @RequestBody UpdateLectureRequestDto updatePostRequestDto, 
        @AuthenticationPrincipal UserDetailsImpl userDetails) 
    {
        var data = lectureService.updateLecture(lectureid, updatePostRequestDto, userDetails.getMember());
        return ToResponse.of(data, SuccessCode.LECTURE_UPDATE_OK);
    }

    // 수업 삭제
    @DeleteMapping(value = "/{lectureid}")
    public ResponseEntity<?> deleteLecture(@PathVariable Long lectureid, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        lectureService.deleteLecture(lectureid, userDetails.getMember());
        return ToResponse.of(null, SuccessCode.LECTURE_DELETE_OK);
    }

    // 수업 단일 조회
    @GetMapping(value ="/{lectureid}")
    public ResponseEntity<?> getLecture(
        @PathVariable Long lectureid,
        @AuthenticationPrincipal UserDetailsImpl userDtails
    )
    {
        LectureResponseDto data = lectureService.getLecture(lectureid);
        return ToResponse.of(data, SuccessCode.LECTURE_GETLECTURE_OK);
    }

    // 수업 전체 조회
    @GetMapping
    public ResponseEntity<?> getAllLeuctures(
        @RequestParam(required = false, value = "category") int category,
        @ModelAttribute PageRequestDto pageable
    ){
        var data = boardService.getAllPost(category, pageable.toPageable());
        return ToResponse.of(data, SuccessCode.LECTURE_GETLECTURES_OK);
    }

    // 나의 수업 전체 조회
    @GetMapping("/mylectures")
    public ResponseEntity<?> getMyAllLectures(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @ModelAttribute PageRequestDto pageable
    )
    {
        var data = boardService.getLecturesByMemberId(userDetails.getMember().getId(), pageable.toPageable());
        return ToResponse.of(data, SuccessCode.LECTURE_GETLECTURES_OK);
    }
    
    // 나의 수업인지 확인
    @GetMapping("/{lectureid}/check")
    public ResponseEntity<?> checkLectureAuthor(
        @PathVariable Long lectureid,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        boolean isMyPost = lectureService.checkLectureAuthor(lectureid, userDetails.getMember());
        return ToResponse.of(isMyPost, SuccessCode.LECTURE_CHECK_OK);
    }
    
    // 현재 글의 즐겨찾기 상태 확인
    @GetMapping(value = "/bookmark/{postId}")
    public ResponseEntity<?> getBookmarkState(
        @PathVariable Long postId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    )
    {
        var data = favMemberPostService.getState(postId, userDetails.getMember());
        return ResponseEntity.ok().body(data);
    }

    //즐겨찾기 추가 및 취소
    @PostMapping(value = "/bookmark/{lectureid}")
    public ResponseEntity<?> updateBookmark(
        @PathVariable Long lectureid,
        @AuthenticationPrincipal UserDetailsImpl userDetails) 
    {
        var data = favMemberPostService.updateBookmark(lectureid, userDetails.getMember());
        return ToResponse.of(data, SuccessCode.LECTURE_UPDATEBOOKMARK_OK);
    }
    
    //즐겨찾기된 수업 전체 조회
    @GetMapping(value = "/bookmark")
    public ResponseEntity<?> getLecturesByBookmarked(
        @ModelAttribute PageRequestDto pageRequest,
        @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        var data = favMemberPostService.getLecturesByBookmarked(pageRequest.toPageable(), userDetails.getMember());
        return ToResponse.of(data, SuccessCode.LECTURE_GETBOOKMARKEDLECTURES_OK);
    }

    //키워드로 상품 검색하기
    @GetMapping("/search")
    public Page<LectureResponseDto> searchByKeyword(
        @ModelAttribute PageRequestDto pageRequestDto,
        @RequestParam String keyword){
        return lectureService.searchByKeyword(keyword,pageRequestDto.toPageable());
    }

    //완료된 수업중 리뷰작성이 안된 수업조회
    @GetMapping("/reviewfilter")
    public ResponseEntity<?> reviewFilter(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        var data = lectureService.reviewFilter(userDetails.getMember());
        return ToResponse.of(data, SuccessCode.LECTURE_GETLECTURE_OK);
    }

}
