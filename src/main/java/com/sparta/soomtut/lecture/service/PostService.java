package com.sparta.soomtut.lecture.service;

import com.sparta.soomtut.dto.request.CategoryRequestDto;
import com.sparta.soomtut.dto.request.PageRequestDto;
import com.sparta.soomtut.entity.Category;
import com.sparta.soomtut.lecture.dto.request.PostRequestDto;
import com.sparta.soomtut.lecture.dto.request.UpdatePostRequestDto;
import com.sparta.soomtut.lecture.dto.response.PostResponseDto;
import com.sparta.soomtut.lecture.entity.Post;
import com.sparta.soomtut.member.entity.Member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PostService {
//     글작성
    PostResponseDto createPost(Member member, PostRequestDto postRequestDto);

//     글수정
    PostResponseDto updatePost(Long postId, UpdatePostRequestDto updatePostRequestDto, Member member);

    void deletePost(Long postId, Member member);

    PostResponseDto getPost(Long postId);

    String createCategory(CategoryRequestDto categoryRequestDto);

    boolean isMyPost(Long postId, Member member);

    List<Category> getCategory();
    String classConfirmed(Long postId, Member member);
    String classComplete(Long postId, Member member);
    List<Post> getCompletePost(Member member);
    //Page<Post> getReviewFilter(PageRequestDto pageRequestDto, Member member);

    Post getPostById(Long postId);
    Long getTutorId(Long postId);
    PostResponseDto getMyPost(Member member);

    Page<Post> getAllPostByMemberId(Long memberId, Pageable pageable);
    Page<Post> getPosts(Pageable pageable);
    Page<Post> getPosts(Long category, Pageable pageable);

    Page<PostResponseDto> searchByKeyword(String keyword,Pageable pageable);

}