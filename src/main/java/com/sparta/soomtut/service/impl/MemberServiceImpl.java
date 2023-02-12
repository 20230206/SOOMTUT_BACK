package com.sparta.soomtut.service.impl;

import com.sparta.soomtut.entity.Member;
import com.sparta.soomtut.repository.MemberRepository;
import com.sparta.soomtut.service.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final LocationServiceImpl locationService;



    @Override
    @Transactional
    public Member saveMember(Member member){
        return memberRepository.save(member);
    }
    
    @Override
    @Transactional
    public boolean existsMemberByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public boolean existsMemberByNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);    
    }

    @Override
    @Transactional
    public Member findMemberById(Long memberId){
        Member foundMember = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다!")
        );
        return foundMember;
    }

    @Transactional
    public String updateNickname(String nickname, Member member){

        Member foundMember = findMemberById(member.getId());

        foundMember.updateNickName(nickname);

        return "수정이 완료되었습니다!";
    }

    public String getNickname(Member member) {

        Member foundMember = findMemberById(member.getId());
        return foundMember.getNickname();

    }

    public String getLocation(Member member) {
        Member foundMember = findMemberById(member.getId());

        return locationService.getLocation(foundMember).getAddress();

    }

    public LocalDate getSignupDate(Member member) {
        Member foundMember  = findMemberById(member.getId());

        return foundMember.getCreatedAt();

    }

    public int getLevel(Member member) {
        Member foundMember  = findMemberById(member.getId());
        return foundMember.getLevel();
    }

    public String getImage(Member member) {
        Member foundMember  = findMemberById(member.getId());
        return foundMember.getImage();
    }
}
