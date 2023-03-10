package com.sparta.soomtut.lecture.controller;

import com.sparta.soomtut.image.service.S3Service;
import com.sparta.soomtut.lecture.dto.request.CreateLectureRequestDto;
import com.sparta.soomtut.lecture.dto.response.LectureResponseDto;
import com.sparta.soomtut.lecture.service.BookmarkService;
import com.sparta.soomtut.lecture.service.LectureService;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/lecture")
public class LectureController {
    private final LectureService lectureService;
    private final BoardServiceImpl boardService;
    private final BookmarkService bookmarkService;
    private final S3Service s3Service;
    
    // 수업 등록
    @PostMapping
    public ResponseEntity<?> createLecture(
            @RequestPart CreateLectureRequestDto postRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart("file") MultipartFile file) throws IOException
    {
        var data = lectureService.createLecture(userDetails.getMember(), postRequestDto,file);
        ToResponse.of(data, SuccessCode.LECTURE_CREATE_OK);
        s3Service.uploadLectureImage(data.getId(),file);
        return ToResponse.of(data, SuccessCode.IMG_LECTUREIMG_OK);
    }

    // 수업 수정
    @PutMapping("/{lectureid}")
    public ResponseEntity<?> updateLecture(
            @PathVariable Long lectureid,
            @RequestPart CreateLectureRequestDto postRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart(value = "file",required = false) MultipartFile file) throws IOException
    {
        var data = lectureService.updateLecture(lectureid, postRequestDto, userDetails.getMember(),file);
        if (file!=null) {
            s3Service.uploadLectureImage(data.getId(), file);
        }
        return ToResponse.of(data, SuccessCode.LECTURE_UPDATE_OK);

    }

    // 수업 삭제
    @DeleteMapping(value = "/{lectureid}")
    public ResponseEntity<?> deleteLecture(
            @PathVariable Long lectureid,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        lectureService.deleteLecture(lectureid, userDetails.getMember());
        return ToResponse.of(null, SuccessCode.LECTURE_DELETE_OK);
    }

    // 수업 단일 조회
    @GetMapping(value ="/public/{lectureid}")
    public ResponseEntity<?> getLecture(
            @PathVariable Long lectureid)
    {
        LectureResponseDto data = lectureService.getLecture(lectureid);
        return ToResponse.of(data, SuccessCode.LECTURE_GETLECTURE_OK);
    }

    // 수업 전체 조회
    @GetMapping(value="/public")
    public ResponseEntity<?> getAllLeuctures(
            @RequestParam(required = false, value = "category") int category,
            @RequestParam(required = false, value = "region") String region,
            @ModelAttribute PageRequestDto pageable)
    {
        var data = boardService.getAllPost(category, pageable.toPageable());
        return ToResponse.of(data, SuccessCode.LECTURE_GETLECTURES_OK);
    }

    // 나의 수업 전체 조회
    @GetMapping("/mylectures")
    public ResponseEntity<?> getMyAllLectures(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @ModelAttribute PageRequestDto pageable)
    {
        var data = boardService.getLecturesByMemberId(userDetails.getMember().getId(), pageable.toPageable());
        return ToResponse.of(data, SuccessCode.LECTURE_GETMYLECTURES_OK);
    }
    
    // 나의 수업인지 확인
    @GetMapping("/{lectureid}/check")
    public ResponseEntity<?> checkLectureAuthor(
            @PathVariable Long lectureid,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        boolean isMyPost = lectureService.checkLectureAuthor(lectureid, userDetails.getMember());
        return ToResponse.of(isMyPost, SuccessCode.LECTURE_CHECK_OK);
    }
    
    // 현재 글의 즐겨찾기 상태 확인
    @GetMapping(value = "/bookmark/{lectureId}")
    public ResponseEntity<?> getBookmarkState(
            @PathVariable Long lectureId,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        var data = bookmarkService.getState(lectureId, userDetails.getMember());
        return ToResponse.of(data, SuccessCode.LECTURE_BOOKMARKCHECK_OK);
    }

    //즐겨찾기 추가 및 취소
    @PostMapping(value = "/bookmark/{lectureId}")
    public ResponseEntity<?> updateBookmark(
            @PathVariable Long lectureId,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        var data = bookmarkService.updateBookmark(lectureId, userDetails.getMember());
        return ToResponse.of(data, SuccessCode.LECTURE_UPDATEBOOKMARK_OK);
    }

    
    //즐겨찾기된 수업 전체 조회
    @GetMapping(value = "/bookmark")
    public ResponseEntity<?> getLecturesByBookmarked(
            @ModelAttribute PageRequestDto pageRequest,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        var data = bookmarkService.getLecturesByBookmarked(pageRequest.toPageable(), userDetails.getMember());
        return ToResponse.of(data, SuccessCode.LECTURE_GETBOOKMARKEDLECTURES_OK);
    }

    //키워드로 수업 검색하기
    @GetMapping("/public/search")
    public Page<LectureResponseDto> searchByKeyword(
            @ModelAttribute PageRequestDto pageRequestDto,
            @RequestParam String keyword)
    {
        return lectureService.searchByKeyword(keyword,pageRequestDto.toPageable());
    }

    //특정 회원 수업 모두 조회
    @GetMapping(value ="/public/{memberId}/all")
    public ResponseEntity<?> getMemberLecture(
            @PathVariable Long memberId,
            @RequestParam(required = false, value = "category") int category,
            @RequestParam(required = false, value = "region") String region,
            @ModelAttribute PageRequestDto pageRequestDto)
    {
        Page<LectureResponseDto> data = lectureService.getMemberLecture(category,memberId,pageRequestDto.toPageable());
        return ToResponse.of(data, SuccessCode.LECTURE_GETLECTURES_OK);
    }


    @GetMapping(value = "/public/popular")
    public ResponseEntity<?> getPopularLectures() {
       var date =  lectureService.getPopularLectures();
        return ToResponse.of(date, SuccessCode.LECTURE_POPULARLECTURES_OK);
    }

}
