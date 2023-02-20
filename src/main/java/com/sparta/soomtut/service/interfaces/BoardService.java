package com.sparta.soomtut.service.interfaces;

import com.sparta.soomtut.dto.response.PostResponseDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {
    Page<PostResponseDto> getPostsByMemberId(Long memberId, Pageable pageable);
    Page<PostResponseDto> getAllPost(Long category, Pageable pageable);
}
