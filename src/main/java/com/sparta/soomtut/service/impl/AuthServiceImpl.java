package com.sparta.soomtut.service.impl;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.soomtut.dto.request.SigninRequestDto;
import com.sparta.soomtut.dto.request.SignupRequestDto;
import com.sparta.soomtut.dto.response.SigninResponseDto;
import com.sparta.soomtut.entity.Member;
import com.sparta.soomtut.exception.ErrorCode;
import com.sparta.soomtut.service.interfaces.AuthService;
import com.sparta.soomtut.service.interfaces.MemberService;
import com.sparta.soomtut.util.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;

// lombok
@RequiredArgsConstructor

// jpa
@Service
public class AuthServiceImpl implements AuthService {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    @Transactional
    public void signup(SignupRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String nickname = requestDto.getNickname();

        if(memberService.existsMemberByEmail(email))
            throw new IllegalArgumentException(ErrorCode.DUPLICATED_EMAIL.getMessage());
        
        if(memberService.existsMemberByNickname(nickname))
            throw new IllegalArgumentException(ErrorCode.DUPLICATED_NICKNAME.getMessage());

        Member member = new Member(email, password, nickname);
        memberService.saveMember(member);
    }

    @Override
    @Transactional
    public SigninResponseDto signin(SigninRequestDto requestDto) {
        String email = requestDto.getEmail();      
        String password = requestDto.getPassword();
        
        Member member = memberService.findMemberByEmail(email);

        if(!isMatchedPassword(password, member)) {
            throw new IllegalArgumentException(ErrorCode.INVALID_PASSWORD.getMessage());
        }

        if (!member.isState()) {
            throw new IllegalArgumentException(ErrorCode.SECESSION_USER.getMessage());
        }

        // 토큰 생성
        String token = jwtProvider.createToken(member.getEmail(), member.getMemberRole());
        
        return SigninResponseDto.builder().token(token).build();
    }


    private boolean isMatchedPassword(String input, Member member) {
        return passwordEncoder.matches(input, member.getPassword());
    }
}
