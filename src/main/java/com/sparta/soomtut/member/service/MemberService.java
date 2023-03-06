package com.sparta.soomtut.member.service;

import com.sparta.soomtut.member.dto.response.MemberResponse;
import com.sparta.soomtut.member.entity.Member;

import java.util.Optional;

public interface MemberService {

    MemberResponse updateNickname(String nickname, Member member);
    String getNickname(Member member);
    MemberResponse suspendAccount(Long memberId);

    MemberResponse getMemberInfo(Long memberId);

    // Repository 지원 함수
    Member getMemberById(Long memberId);
    Member getMemberByEmail(String email);
    Member saveMember(Member member);
    Member getMemberByNickname(String nickname);
    boolean existsMemberByEmail(String email);
    boolean existsMemberByNickname(String nickname);
    String deleteReviewRequest(Long reviewId);
    Optional<Member> findByProviderAndOauthEmail(String provider, String email);

}
