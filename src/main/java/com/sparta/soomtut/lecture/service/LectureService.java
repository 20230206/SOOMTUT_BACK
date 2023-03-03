package com.sparta.soomtut.lecture.service;

import com.sparta.soomtut.lecture.dto.request.CategoryRequestDto;
import com.sparta.soomtut.lecture.dto.request.CreateLectureRequestDto;
import com.sparta.soomtut.lecture.dto.request.UpdateLectureRequestDto;
import com.sparta.soomtut.lecture.dto.response.LectureResponseDto;
import com.sparta.soomtut.lecture.entity.Category;
import com.sparta.soomtut.lecture.entity.Lecture;
import com.sparta.soomtut.member.entity.Member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LectureService {

//     글작성
    LectureResponseDto createLecture(Member member, CreateLectureRequestDto lectureRequestDto, MultipartFile file);
//     글수정
    LectureResponseDto updateLecture(Long lectureId, UpdateLectureRequestDto lectureRequestDto, Member member);
    void deleteLecture(Long lectureId, Member member);
    LectureResponseDto getLecture(Long lectureId);
    boolean checkLectureAuthor(Long postId, Member member);
    //Page<Post> getReviewFilter(PageRequestDto pageRequestDto, Member member);
    Lecture getLectureById(Long lectureId);
    Long getTutorId(Long lectureId);
    LectureResponseDto getMyLecture(Member member);
    Page<Lecture> getAllLectureByMemberId(Long memberId, Pageable pageable);
    Page<Lecture> getLectures(Pageable pageable);
    Page<Lecture> getLectures(int category, Pageable pageable);
    Page<LectureResponseDto> getMemberLecture(int category,Long memberId,Pageable pageable);
    Page<Lecture> getMemberLectures(int category,Long memberId,Pageable pageable);
    Page<LectureResponseDto> searchByKeyword(String keyword,Pageable pageable);

}
