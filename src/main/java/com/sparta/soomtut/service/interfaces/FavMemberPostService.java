package com.sparta.soomtut.service.interfaces;

import com.sparta.soomtut.dto.request.FavPostDto;
import com.sparta.soomtut.dto.response.PostResponseDto;

import java.awt.print.Pageable;
import java.util.List;

public interface FavMemberPostService {
    String updateOfFavPost(FavPostDto favPostDto);
    List<PostResponseDto> findAllFavPosts(Pageable pageable);
    PostResponseDto findFavPost(Long id);

}
