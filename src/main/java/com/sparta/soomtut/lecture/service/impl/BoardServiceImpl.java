package com.sparta.soomtut.lecture.service.impl;

import com.sparta.soomtut.lecture.dto.response.PostResponseDto;
import com.sparta.soomtut.lecture.entity.Post;
import com.sparta.soomtut.lecture.service.BoardService;
import com.sparta.soomtut.lecture.service.PostService;
import com.sparta.soomtut.service.interfaces.LocationService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final PostService postService;
    
    private final LocationService locationService;

    @Override
    @Transactional(readOnly = true)
    public Page<PostResponseDto> getPostsByMemberId(Long memberId, Pageable pageable) {
        Page<Post> posts = postService.getAllPostByMemberId(memberId, pageable);

        return posts.map(item -> new PostResponseDto(item,
                                         item.getMember().getNickname(),
                                         locationService.getLocation(item.getMember()).getAddress()));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostResponseDto> getAllPost(Long category, Pageable pageable) {

        if(category == 0 ){
            Page<Post> posts = postService.getPosts(pageable);
            return posts.map(item -> new PostResponseDto(item));
        }
        else {
            Page<Post> posts = postService.getPosts(category, pageable);
            return posts.map(item -> new PostResponseDto(item));
        }

    }


}
