package com.sparta.soomtut.service.impl;

import com.sparta.soomtut.dto.PostResponseDto;
import com.sparta.soomtut.entity.Post;
import com.sparta.soomtut.repository.LocationRepository;
import com.sparta.soomtut.repository.MemberRepository;
import com.sparta.soomtut.repository.PostRepository;
import com.sparta.soomtut.service.interfaces.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final LocationRepository locationRepository;

    @Override
    public List<PostResponseDto> getMyPosts(Long memberId) {
        List<Post> posts = postRepository.findAllByTutorId(memberId);
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        posts.forEach(post -> postResponseDtoList.add(new PostResponseDto(post,
                memberRepository.findById(memberId).get().getNickname(),
                locationRepository.findByMemberId(memberId).get().getAddress()
                )));
        return postResponseDtoList;
    }
}
