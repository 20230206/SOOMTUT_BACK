package com.sparta.soomtut.service.impl;
import com.sparta.soomtut.service.interfaces.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.sparta.soomtut.repository.MemberRepository;
import com.sparta.soomtut.dto.SigninRequestDto;
import com.sparta.soomtut.entity.Member;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;


    @Transactional
    public String updateNickname(String nickname, Member member){

        Member foundMember = memberRepository.findById(member.getId()).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다!")
        );

        foundMember.updateNickName(nickname);

        return "수정이 완료되었습니다!";
    }

    // repository 지원 함수
    @Override
    @Transactional
    public Member saveMember(Member member){
        return memberRepository.save(member);
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
    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
            () -> new IllegalArgumentException("등록된 사용자가 없습니다!")
        );
    }

}
