package com.sparta.soomtut.service.interfaces;

import com.sparta.soomtut.dto.CreateReviewRequestDto;
import com.sparta.soomtut.dto.SigninRequestDto;
import com.sparta.soomtut.entity.Member;

import java.time.LocalDate;


public interface MemberService {

    
    // Repository 지원 함수
    Member findMemberByEmail(String email);
    Member saveMember(Member member);
    boolean existsMemberByEmail(String email);
    boolean existsMemberByNickname(String nickname);
    String updateNickname(String nickname, Member member);
    String getNickname(Member member);
    String getLocation(Member member);
    LocalDate getSignupDate(Member member);
    int getLevel(Member member);
    String getImage(Member member);
    String createReview(Long postId, CreateReviewRequestDto reviewRequestDto, Member member);

}
