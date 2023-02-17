package com.sparta.soomtut.service.impl;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.soomtut.dto.request.SigninRequestDto;
import com.sparta.soomtut.dto.request.SignupRequestDto;
import com.sparta.soomtut.dto.response.SigninResponseDto;
import com.sparta.soomtut.dto.response.MemberInfoResponseDto;

import com.sparta.soomtut.entity.Member;
import com.sparta.soomtut.entity.Location;
import com.sparta.soomtut.exception.ErrorCode;
import com.sparta.soomtut.service.interfaces.AuthService;
import com.sparta.soomtut.service.interfaces.LocationService;
import com.sparta.soomtut.service.interfaces.MemberService;
import com.sparta.soomtut.util.jwt.JwtProvider;

import jakarta.servlet.http.HttpServletRequest;



import lombok.RequiredArgsConstructor;

// lombok
@RequiredArgsConstructor

// jpa
@Service
public class AuthServiceImpl implements AuthService {

    private final MemberService memberService;
    private final LocationService locationService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    @Transactional
    public MemberInfoResponseDto signup(SignupRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String nickname = requestDto.getNickname();

        if(memberService.existsMemberByEmail(email))
            throw new IllegalArgumentException(ErrorCode.DUPLICATED_EMAIL.getMessage());
        
        if(memberService.existsMemberByNickname(nickname))
            throw new IllegalArgumentException(ErrorCode.DUPLICATED_NICKNAME.getMessage());

        Member member = memberService.saveMember(Member.userDetailRegister().email(email).password(password).nickname(nickname).build());
        // 위치 정보도 만들어준다
        Location location = locationService.saveLocation(requestDto, member);
        

        return MemberInfoResponseDto.toDto(member, location);
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

        // 토큰 생성
        String token = jwtProvider.createToken(member.getEmail(), member.getMemberRole());
        
        return SigninResponseDto.builder().token(token).build();
    }


    private boolean isMatchedPassword(String input, Member member) {
        return passwordEncoder.matches(input, member.getPassword());
    }

    @Override
    @Transactional(readOnly=true) 
    public boolean checkToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        boolean validation = false;
        if(bearerToken.length()>7)
            validation = jwtProvider.validateToken(bearerToken.substring(7));
        return validation;
    };
}
