package com.sparta.soomtut.service.impl;

import com.sparta.soomtut.entity.Member;
import com.sparta.soomtut.repository.MemberRepository;
import com.sparta.soomtut.service.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final LocationServiceImpl locationService;


    // repository 지원 함수


    @Override
    @Transactional
    public Optional<Member> findMemberById(Long memberId) {
        return memberRepository.findById(memberId);
    }

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
    @Transactional
    public String updateNickname(String nickname, Member member) {

        member.updateNickName(nickname);

        return "수정이 완료되었습니다!";
    }

    @Override
    @Transactional(readOnly = true)
    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
            () -> new IllegalArgumentException("등록된 사용자가 없습니다!")
        );

    }

    @Override
    @Transactional
    public String getNickname(Member member) {

        return member.getNickname();

    }
    @Override
    @Transactional
    public String getLocation(Member member) {

        return locationService.getLocation(member).getAddress();

    }
    @Override
    @Transactional
    public LocalDate getSignupDate(Member member) {

        return member.getCreatedAt();

    }
    @Override
    @Transactional
    public int getLevel(Member member) {
        return member.getLevel();
    }
    @Override
    @Transactional
    public String getImage(Member member) {
        return member.getImage();
    }
}
