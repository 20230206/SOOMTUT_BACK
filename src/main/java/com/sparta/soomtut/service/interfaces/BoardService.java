package com.sparta.soomtut.service.interfaces;

import com.sparta.soomtut.dto.PostResponseDto;

import java.util.List;

public interface BoardService {

    List<PostResponseDto> getAllPost();
}
