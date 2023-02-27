package com.sparta.soomtut.lecture.service;

import com.sparta.soomtut.lecture.dto.request.CategoryRequestDto;
import com.sparta.soomtut.lecture.dto.request.CreateLectureRequestDto;
import com.sparta.soomtut.lecture.dto.request.UpdateLectureRequestDto;
import com.sparta.soomtut.lecture.dto.response.LectureResponseDto;
import com.sparta.soomtut.lecture.entity.Category;
import com.sparta.soomtut.lecture.entity.Lecture;
import com.sparta.soomtut.member.entity.Member;
import com.sparta.soomtut.util.dto.request.PageRequestDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface LectureService {
//     글작성
    LectureResponseDto createLecture(Member member, CreateLectureRequestDto postRequestDto);

//     글수정
    LectureResponseDto updateLecture(Long postId, UpdateLectureRequestDto updatePostRequestDto, Member member);

    void deleteLecture(Long postId, Member member);

    LectureResponseDto getPost(Long postId);

    String createCategory(CategoryRequestDto categoryRequestDto);

    boolean checkLectureAuthor(Long postId, Member member);

    List<Category> getCategory();
    String createLectureRequest(Long lectureid, Member member);
    String classConfirmed(Long lecturerequestid, Member member);
    String classComplete(Long lecturerequestid, Member member);
    List<Lecture> getCompletePost(Member member);
    List<Lecture> reviewFilter(Member member);

    Lecture getPostById(Long postId);
    Long getTutorId(Long postId);
    LectureResponseDto getMyPost(Member member);

    Page<Lecture> getAllPostByMemberId(Long memberId, Pageable pageable);
    Page<Lecture> getPosts(Pageable pageable);
    Page<Lecture> getPosts(Long category, Pageable pageable);

    Page<LectureResponseDto> searchByKeyword(String keyword,Pageable pageable);

}
