package com.sparta.soomtut.member.service.impl;

import com.sparta.soomtut.location.entity.Location;
import com.sparta.soomtut.member.dto.response.MemberResponse;
import com.sparta.soomtut.member.entity.Member;
import com.sparta.soomtut.member.entity.enums.MemberState;
import com.sparta.soomtut.member.repository.MemberRepository;
import com.sparta.soomtut.member.service.MemberService;
import com.sparta.soomtut.util.response.ErrorCode;
import com.sparta.soomtut.admin.service.DeleteReviewRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final DeleteReviewRequestService deleteReviewRequestService;

    @Override
    @Transactional
    public MemberResponse updateNickname(String nickname, Member memberId) {
        Member member = getMemberById(memberId.getId());
        member.updateNickName(nickname);
        return MemberResponse.toDto().member(member).build();
    }

    @Override
    public String getNickname(Member member) {
        return member.getNickname();
    }
    
    @Override
    @Transactional
    public MemberResponse suspendAccount(Long memberId) {
        Member member = getMemberById(memberId);
        member.changeState(MemberState.SUSPEND);
        return MemberResponse.toDto().member(member).build();
    }

    @Override
    @Transactional
    public MemberResponse getMemberInfo(Long memberId) {
        Member member = getMemberById(memberId);
        return MemberResponse.toDto().member(member).build();
    }



    // repository 지원 함수
    @Override
    @Transactional
    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                ()->new IllegalArgumentException(ErrorCode.NOT_FOUND_USER.getMessage()));
    }

    @Override
    @Transactional
    public Member saveMember(Member member){
        return memberRepository.save(member);
    }

    @Override
    public Member getMemberByNickname(String nickname) {
        return memberRepository.findByNickname(nickname)
                .orElseThrow(()->new IllegalArgumentException(ErrorCode.NOT_FOUND_USER.getMessage()));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsMemberByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsMemberByNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);    
    }

    @Override
    @Transactional(readOnly = true)
    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
            () -> new IllegalArgumentException("등록된 사용자가 없습니다!")
        );

    }

    @Override
    public String deleteReviewRequest(Long reviewId) {
        return deleteReviewRequestService.deleteReviewRequest(reviewId);
    }

    @Override
    @Transactional
    public Optional<Member> findByProviderAndOauthEmail(String provider, String email) {
        return memberRepository.findByProviderAndOauthEmail(provider, email);
    }


    @Override
    @Transactional(readOnly=true)
    public List<MemberResponse> getAllLocation(Location myLocation,Member member) {
        // TODO : 변경
         String myCitySido = myLocation.getSido();
         List<Member> cityUserLocations = memberRepository.findAllByAddress(myCitySido,member.getId());



        return cityUserLocations.stream().map(item -> MemberResponse.toDto().member(item).build()).toList();
}

}
