package com.sparta.soomtut.lecture.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sparta.soomtut.lecture.dto.response.PostResponseDto;

public interface BoardService {
    Page<PostResponseDto> getPostsByMemberId(Long memberId, Pageable pageable);
    Page<PostResponseDto> getAllPost(Long category, Pageable pageable);
}
