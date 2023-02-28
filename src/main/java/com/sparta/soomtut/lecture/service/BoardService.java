package com.sparta.soomtut.lecture.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sparta.soomtut.lecture.dto.response.LectureResponseDto;

public interface BoardService {
    Page<LectureResponseDto> getPostsByMemberId(Long memberId, Pageable pageable);
    Page<LectureResponseDto> getAllPost(int category, Pageable pageable);
}
