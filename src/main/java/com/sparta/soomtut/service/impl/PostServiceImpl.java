package com.sparta.soomtut.service.impl;

import com.sparta.soomtut.entity.Post;
import com.sparta.soomtut.repository.PostRepository;
import com.sparta.soomtut.service.interfaces.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    @Transactional
    public Post findPostById(Long postId){
       Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("찾으시는 게시물이 없습니다!")
        );

        return post;
    }
    @Override
    @Transactional
    public Long getTutorId(Long postId) {

        return findPostById(postId).getTutorId();

    }
}
