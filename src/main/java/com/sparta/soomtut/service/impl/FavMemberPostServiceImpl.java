package com.sparta.soomtut.service.impl;

import com.sparta.soomtut.dto.response.PostResponseDto;
import com.sparta.soomtut.entity.FavMemberPost;
import com.sparta.soomtut.entity.Member;
import com.sparta.soomtut.entity.Post;
import com.sparta.soomtut.exception.ErrorCode;
import com.sparta.soomtut.repository.FavMemberPostRepository;
import com.sparta.soomtut.service.interfaces.FavMemberPostService;
import com.sparta.soomtut.service.interfaces.PostService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavMemberPostServiceImpl implements FavMemberPostService {
    private final FavMemberPostRepository favMemberPostRepository;
    private final PostService postService;

    @Transactional
    @Override
    public boolean getState(Long postId, Member member) {
        if(!hasFavPost(postId, member)) return false;
        else {
            FavMemberPost fav = findByPostIdAndMemberId(postId, member.getId());
            return fav.isStatus();
        }

    }
    
    //즐겨찾기 특정 Id 조회
    @Transactional
    @Override
    public PostResponseDto findFavPost(Long id){
        Post post = postService.findPostById(id);
        FavMemberPost favMemberPost = favMemberPostRepository.findByPostId(post.getId())
                .orElseThrow(()->new IllegalArgumentException(ErrorCode.NOT_FOUND_FAVPOST.getMessage()));
        return new PostResponseDto(favMemberPost.getPost());
    }

    //즐겨찾기 전체 조회
    @Transactional
    @Override
    public Page<PostResponseDto> findAllFavPosts(Pageable pageable, Member member){
        // PageRequest pageables = PageRequest.of(reqeust.getPage(), 5);
        Page<FavMemberPost> favlist = favMemberPostRepository.findAllByMemberIdAndStatusIsTrue(member.getId(), pageable);
        return favlist.map((item) -> new PostResponseDto(item.getPost()));
    }
    

    //즐겨찾기 업데이트
    @Transactional
    @Override
    public boolean updateOfFavPost(Long postId, Member member) {
        // 1. existsByPostIdAndMemberId를 통해서 return이 false 일시
        //  -> 새로운 FavMemberPost를 생성하고, save 해주고 return true
        // 2. existsByPostIdAndMemberId를 통해서 return이 true 일시
        //  -> findByPostIdAndMemberId 통해 해당 기록을 가져오고, 해당기록의 내용을 반대로 변경시켜준다.
        
        if(!hasFavPost(postId, member)) {
            Post post = postService.findPostById(postId);
            FavMemberPost data = createFavPost(post, member);
            return data.isStatus();
        }
        else {
            FavMemberPost data = findByPostIdAndMemberId(postId, member.getId());
            data.updateState(!data.isStatus());
            return data.isStatus();
        }
    }

    //즐겨찾기 추가
    @Transactional
    public FavMemberPost createFavPost(Post post, Member member){
        return favMemberPostRepository.save(FavMemberPost.builder().post(post).member(member).build());
    }

    //글과 멤버의 값을 가지고 있다면 true 아니라면 false 용도의 함수
    @Transactional(readOnly = true)
    public boolean hasFavPost(Long postId, Member member){
        //Optional 값을 가지고 있다면 ture 아니면 false ->existsBy로 변경
        return favMemberPostRepository.existsByPostIdAndMemberId(postId, member.getId()); 
    }

    @Transactional(readOnly = true)
    private FavMemberPost findByPostIdAndMemberId(Long postId, Long memberId) {
        return favMemberPostRepository.findByPostIdAndMemberId(postId, memberId).orElseThrow(
            () -> new IllegalArgumentException("로그가 존재하지 않습니다.")
        );
    }

}
