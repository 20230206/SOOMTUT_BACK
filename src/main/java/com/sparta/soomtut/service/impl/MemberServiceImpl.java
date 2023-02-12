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


    @Transactional
    public String updateNickname(String nickname, Member member){

        member.updateNickName(nickname);

        return "수정이 완료되었습니다!";
    }

    public String getNickname(Member member) {

        return member.getNickname();

    }

    public String getLocation(Member member) {

        return locationService.getLocation(member).getAddress();

    }

    public LocalDate getSignupDate(Member member) {

        return member.getCreatedAt();

    }

    public int getLevel(Member member) {
        return member.getLevel();
    }

    public String getImage(Member member) {
        return member.getImage();
    }
}
