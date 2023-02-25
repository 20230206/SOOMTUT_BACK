package com.sparta.soomtut.service.interfaces;

import com.sparta.soomtut.dto.response.PostResponseDto;
import com.sparta.soomtut.member.entity.Member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface FavMemberPostService {
    boolean updateOfFavPost(Long postId, Member member);
    boolean getState(Long postId, Member member);

    Page<PostResponseDto> findAllFavPosts(Pageable pageable, Member member);
    PostResponseDto findFavPost(Long id);
}
