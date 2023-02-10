package com.sparta.soomtut.service.impl;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sparta.soomtut.dto.SignupRequestDto;
import com.sparta.soomtut.entity.Member;
import com.sparta.soomtut.service.interfaces.AuthService;
import com.sparta.soomtut.service.interfaces.MemberService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

// lombok
@RequiredArgsConstructor

// jpa
@Service
public class AuthServiceImpl implements AuthService {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void signup(SignupRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String nickname = requestDto.getNickname();

        if(memberService.existsMemberByEmail(email))
            throw new IllegalArgumentException("중복되는 email이 존재합니다.");
        
        if(memberService.existsMemberByNickname(nickname))
            throw new IllegalArgumentException("중복되는 닉네임이 존해합니다.");

        Member member = new Member(email, password, nickname);
        memberService.saveMember(member);
    }
}
