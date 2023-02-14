package com.sparta.soomtut.service.interfaces;

import com.sparta.soomtut.dto.PostResponseDto;
import com.sparta.soomtut.entity.Member;
import com.sparta.soomtut.entity.Post;

public interface PostService {

    Post findPostById(Long postId);
    Long getTutorId(Long postId);
    PostResponseDto getMyPost(Member member);
}
