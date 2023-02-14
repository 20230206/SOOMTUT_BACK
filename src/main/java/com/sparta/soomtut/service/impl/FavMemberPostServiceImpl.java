package com.sparta.soomtut.service.impl;

import com.sparta.soomtut.dto.request.FavPostDto;
import com.sparta.soomtut.entity.FavMemberPost;
import com.sparta.soomtut.entity.Member;
import com.sparta.soomtut.entity.Post;
import com.sparta.soomtut.exception.FavNotFoundException;
import com.sparta.soomtut.repository.FavMemberPostRepository;
import com.sparta.soomtut.service.interfaces.FavMemberPostService;
import com.sparta.soomtut.service.interfaces.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavMemberPostServiceImpl implements FavMemberPostService {
    private final FavMemberPostRepository favMemberPostRepository;
    private final PostService postService;

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
        FavMemberPost favMemberPost = favMemberPostRepository.findByPostAndMember(post,member)
                .orElseThrow(FavNotFoundException::new);
        favMemberPostRepository.delete(favMemberPost);
        return "SUCCESS_UnFavPost";
    }
    //글과 멤버의 값을 가지고 있다면 true 아니라면 false 용도의 함수
    public boolean hasFavPost(Long id, Member member){
        return favMemberPostRepository.existsByPostIdAndMember(id, member); //Optional 값을 가지고 있다면 ture 아니면 false ->existsBy로 변경
    }
}
