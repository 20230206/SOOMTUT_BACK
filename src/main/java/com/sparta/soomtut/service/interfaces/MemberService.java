package com.sparta.soomtut.service.interfaces;

import com.sparta.soomtut.dto.request.CreateReviewRequestDto;
import com.sparta.soomtut.dto.request.PageRequestDto;
import com.sparta.soomtut.dto.response.MemberInfoResponseDto;
import com.sparta.soomtut.entity.Member;
import com.sparta.soomtut.entity.Review;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface MemberService {

    String updateNickname(String nickname, Member member);
    String getNickname(Member member);
    String getLocation(Member member);

    LocalDate getSignupDate(Member member);
    
    int getLevel(Member member);
    String getImage(Member member);
    
    String createReview(Long postId, CreateReviewRequestDto reviewRequestDto, Member member);

    String deleteAccount(Long memberId);

    // Repository 지원 함수
    Member getMemberById(Long memberId);
    Member getMemberByEmail(String email);
    Member saveMember(Member member);
    Member getMemberByNickname(String nickname);

    boolean existsMemberByEmail(String email);
    boolean existsMemberByNickname(String nickname);

    Page<Review> getReview(PageRequestDto pageRequestDto, Member member);

    String deleteReviewRequest(Long reviewId);
    MemberInfoResponseDto getMemberInfo(Member member);

    MemberInfoResponseDto getMemberInfoResponseDto(Long memberId);
}
