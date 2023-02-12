package com.sparta.soomtut.service.interfaces;

import com.sparta.soomtut.dto.request.PostRequestDto;
import com.sparta.soomtut.dto.request.UpdatePostRequestDto;
import com.sparta.soomtut.dto.response.PostResponseDto;
import com.sparta.soomtut.entity.Member;

public interface PostService {
    // 글작성
    PostResponseDto createPost(Member member, PostRequestDto postRequestDto);

    // 글수정
    PostResponseDto updatePost(Long postId, UpdatePostRequestDto updatePostRequestDto);

    void deletePost(Long postId);
}
