package com.sparta.soomtut.service.impl;

import com.sparta.soomtut.dto.request.FavPostDto;
import com.sparta.soomtut.dto.response.PostResponseDto;
import com.sparta.soomtut.entity.FavMemberPost;
import com.sparta.soomtut.entity.Member;
import com.sparta.soomtut.entity.Post;
import com.sparta.soomtut.exception.ErrorCode;
import com.sparta.soomtut.exception.FavNotFoundException;
import com.sparta.soomtut.repository.FavMemberPostRepository;
import com.sparta.soomtut.service.interfaces.FavMemberPostService;
import com.sparta.soomtut.service.interfaces.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavMemberPostServiceImpl implements FavMemberPostService {
    private final FavMemberPostRepository favMemberPostRepository;
    private final PostService postService;

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
    public List<PostResponseDto> findAllFavPosts(Pageable pageable){
        return postService.getMyPosts(pageable).stream().map(PostResponseDto::new).collect(Collectors.toList());
    }
    //즐겨찾기 업데이트
    @Transactional
    @Override
    public String updateOfFavPost(FavPostDto favPostDto){
        Long Id = favPostDto.getId();
        Member member = favPostDto.getMember();
        Post post = postService.findPostById(favPostDto.getId()); //postSevice에 이미 구현된 기능 사용

        if(!hasFavPost(Id,member)){
            //메소드에 값을 가지고 있지 않다면 post에 favorit 카운트+1 그리고 favMemberPost에 저장
            post.increFavCount();
            return createFavPost(post, member);
        }
        post.decreFavCount();
        return removeFavPost(post, member);
    }
    //즐겨찾기 추가
    @Transactional
    public String createFavPost(Post post, Member member){
        FavMemberPost favMemberPost = new FavMemberPost(post, member);
        favMemberPostRepository.save(favMemberPost);
        return "SUCCESS_FavPost";
    }
    //즐겨찾기 취소
    @Transactional
    public String removeFavPost(Post post, Member member){
        FavMemberPost favMemberPost = favMemberPostRepository.findByPostIdAndMemberId(post.getId(),member.getId())
                .orElseThrow(FavNotFoundException::new);
        favMemberPostRepository.delete(favMemberPost);
        return "SUCCESS_UnFavPost";
    }
    //글과 멤버의 값을 가지고 있다면 true 아니라면 false 용도의 함수
    public boolean hasFavPost(Long id, Member member){
        return favMemberPostRepository.existsByPostIdAndMemberId(id, member.getId()); //Optional 값을 가지고 있다면 ture 아니면 false ->existsBy로 변경
    }
}
