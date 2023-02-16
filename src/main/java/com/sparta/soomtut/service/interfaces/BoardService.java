package com.sparta.soomtut.service.interfaces;

import com.sparta.soomtut.dto.response.PostResponseDto;

import java.util.List;

public interface BoardService {
    public List<PostResponseDto> getMyPosts(Long memberId);

    List<PostResponseDto> getAllPost();
    List<PostResponseDto> getAllPost(Long category);
}
