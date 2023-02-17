package com.sparta.soomtut.service.interfaces;

import com.sparta.soomtut.entity.Member;
import com.sparta.soomtut.dto.request.FavPostDto;
import com.sparta.soomtut.dto.response.PostResponseDto;

import java.awt.print.Pageable;
import java.util.List;


public interface FavMemberPostService {
    boolean updateOfFavPost(Long postId, Member member);
    boolean getState(Long postId, Member member);

    List<PostResponseDto> findAllFavPosts(Pageable pageable);
    PostResponseDto findFavPost(Long id);
}
