package com.sparta.soomtut.service.impl;

import com.sparta.soomtut.dto.request.PostRequestDto;
import com.sparta.soomtut.dto.request.UpdatePostRequestDto;
import com.sparta.soomtut.dto.response.PostResponseDto;
import com.sparta.soomtut.entity.Member;
import com.sparta.soomtut.entity.Post;
import com.sparta.soomtut.exception.ErrorCode;
import com.sparta.soomtut.repository.MemberRepository;
import com.sparta.soomtut.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl {

    private final PostRepository postRepository;


    // 게시글 작성
    public PostResponseDto createPost(String member, PostRequestDto postRequestDto) {
        Post post = new Post(member, postRequestDto.getTitle(), postRequestDto.getImage(), postRequestDto.getContent(), postRequestDto.getFee());
        postRepository.save(post);
        return new PostResponseDto(post);
    }

    // 게시글 수정
    public PostResponseDto updatePost(Long postId, UpdatePostRequestDto updatePostRequestDto) {
        Post post = postRepository.findByPostId(postId).orElseThrow(
                () -> new IllegalArgumentException(ErrorCode.EMPTY_POST.getMessage())
        );
        post.update(updatePostRequestDto);
        return new PostResponseDto(post);
    }

    //게시글 삭제
    public void deletePost(Long postId) {
        Post post = postRepository.findByPostId(postId).orElseThrow(
                () -> new IllegalArgumentException(ErrorCode.EMPTY_POST.getMessage())
        );
        postRepository.delete(post);
    }

}
