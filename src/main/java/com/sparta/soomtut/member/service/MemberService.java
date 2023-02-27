package com.sparta.soomtut.member.service;

import com.sparta.soomtut.member.dto.response.MemberInfoResponse;
import com.sparta.soomtut.member.entity.Member;
import com.sparta.soomtut.review.dto.request.CreateReviewRequestDto;
import com.sparta.soomtut.review.entity.Review;
import com.sparta.soomtut.util.dto.request.PageRequestDto;

import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.Optional;

public interface MemberService {

    String updateNickname(String nickname, Member member);
    String getNickname(Member member);
    String getLocation(Member member);

    LocalDate getSignupDate(Member member);
    
    int getLevel(Member member);
    String getImage(Member member);
    
    String createReview(Long lectureid, CreateReviewRequestDto reviewRequestDto, Member member);

    MemberInfoResponse suspendAccount(Long memberId);

    // Repository 지원 함수
    Member getMemberById(Long memberId);
    Member getMemberByEmail(String email);
    Member saveMember(Member member);
    Member getMemberByNickname(String nickname);

    boolean existsMemberByEmail(String email);
    boolean existsMemberByNickname(String nickname);

    Page<Review> getReview(PageRequestDto pageRequestDto, Member member);

    String deleteReviewRequest(Long reviewId);
    MemberInfoResponse getMemberInfo(Member member);

    MemberInfoResponse getMemberInfoResponseDto(Long memberId);

    Optional<Member> findByProviderAndOauthEmail(String provider, String email);
}
