package com.sparta.soomtut.lecture.service;

import com.sparta.soomtut.dto.request.CategoryRequestDto;
import com.sparta.soomtut.dto.request.PageRequestDto;
import com.sparta.soomtut.lecture.dto.request.CreateLectureRequestDto;
import com.sparta.soomtut.lecture.dto.request.UpdateLectureRequestDto;
import com.sparta.soomtut.lecture.dto.response.LectureResponseDto;
import com.sparta.soomtut.lecture.entity.Category;
import com.sparta.soomtut.lecture.entity.Lecture;
import com.sparta.soomtut.member.entity.Member;

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
    String classConfirmed(Long postId, Member member);
    String classComplete(Long postId, Member member);
    List<Lecture> getCompletePost(Member member);
    //Page<Post> getReviewFilter(PageRequestDto pageRequestDto, Member member);

    Lecture getPostById(Long postId);
    Long getTutorId(Long postId);
    LectureResponseDto getMyPost(Member member);

    Page<Lecture> getAllPostByMemberId(Long memberId, Pageable pageable);
    Page<Lecture> getPosts(Pageable pageable);
    Page<Lecture> getPosts(Long category, Pageable pageable);

    Page<LectureResponseDto> searchByKeyword(String keyword,Pageable pageable);

}
