package com.sparta.soomtut.service.impl;

import com.sparta.soomtut.dto.response.PostResponseDto;
import com.sparta.soomtut.entity.Post;
import com.sparta.soomtut.repository.PostRepository;
import com.sparta.soomtut.service.interfaces.BoardService;
import com.sparta.soomtut.service.interfaces.LocationService;
import com.sparta.soomtut.service.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final PostRepository postRepository;
    private final MemberService memberService;
    private final LocationService locationService;

    @Override
    @Transactional(readOnly = true)
    public List<PostResponseDto> getMyPosts(Long memberId) {
        List<Post> posts = postRepository.findAllByTutorId(memberId);
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        posts.forEach(post -> postResponseDtoList.add(new PostResponseDto(post,
                memberService.findMemberById(memberId).getNickname(),
                locationService.findMemberLocation(memberId).getAddress()
                )));
        return postResponseDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponseDto> getAllPost() {
        List<Post> posts = postRepository.findAll();
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        posts.forEach(post -> postResponseDtoList.add(new PostResponseDto(post,
                memberService.findMemberById(post.getTutorId()).getNickname(),
                locationService.findMemberLocation(post.getTutorId()).getAddress()
        )));
        return postResponseDtoList;
    }

}
