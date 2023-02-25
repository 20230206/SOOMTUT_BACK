package com.sparta.soomtut.lecture.service;

import com.sparta.soomtut.lecture.dto.response.LectureResponseDto;
import com.sparta.soomtut.member.entity.Member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface FavMemberPostService {
    boolean updateBookmark(Long postId, Member member);
    boolean getState(Long postId, Member member);

    Page<LectureResponseDto> getLecturesByBookmarked(Pageable pageable, Member member);
    LectureResponseDto findFavPost(Long id);
}
