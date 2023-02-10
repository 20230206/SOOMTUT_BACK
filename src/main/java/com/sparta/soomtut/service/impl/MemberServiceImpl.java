package com.sparta.soomtut.service.impl;

import com.sparta.soomtut.entity.Member;
import com.sparta.soomtut.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl {

    private MemberRepository memberRepository;

    @Transactional
    public String updateNickname(String nickname, Member member){

        Member foundMember = memberRepository.findById(member.getId()).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다!")
        );

        foundMember.updateNickName(nickname);

        return "수정이 완료되었습니다!";
    }
}
