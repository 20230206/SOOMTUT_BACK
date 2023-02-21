package com.sparta.soomtut.service.impl;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.soomtut.dto.request.RegisterRequest;
import com.sparta.soomtut.dto.request.LoginRequest;
import com.sparta.soomtut.dto.request.OAuthLoginRequest;

import com.sparta.soomtut.dto.response.LoginResponse;
import com.sparta.soomtut.dto.response.MemberInfoResponse;

import com.sparta.soomtut.entity.Member;
import com.sparta.soomtut.entity.Location;

import com.sparta.soomtut.service.interfaces.AuthService;
import com.sparta.soomtut.service.interfaces.LocationService;
import com.sparta.soomtut.service.interfaces.MemberService;

import com.sparta.soomtut.util.enums.MemberRole;
import com.sparta.soomtut.util.jwt.JwtProvider;
import com.sparta.soomtut.util.jwt.TokenType;
import com.sparta.soomtut.util.response.ErrorCode;

import  com.sparta.soomtut.util.exception.CustomException;

import io.jsonwebtoken.Claims;

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
    public MemberInfoResponse register(RegisterRequest requestDto) {
        String email = requestDto.getEmail();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String nickname = requestDto.getNickname();

        if(memberService.existsMemberByEmail(email))
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
        
        if(memberService.existsMemberByNickname(nickname))
            throw new CustomException(ErrorCode.DUPLICATED_NICKNAME);

            // email, password, nickname
        Member member = memberService.saveMember(Member.userDetailRegister().email(email).password(password).nickname(nickname).build());
        Location location = locationService.saveLocation(requestDto, member);

        return MemberInfoResponse.toDto(member, location);
    }

    @Override
    @Transactional
    public LoginResponse login(LoginRequest requestDto) {
        String email = requestDto.getEmail();      
        String password = requestDto.getPassword();
        
        Member member = memberService.getMemberByEmail(email);

        if(!isMatchedPassword(password, member)) 
            throw new CustomException(ErrorCode.INVALID_PASSWORD);

        if (!member.isState()) 
            throw new CustomException(ErrorCode.SECESSION_USER);

        String token = createToken(member.getEmail(), member.getMemberRole(), TokenType.REFRESH);
        
        return LoginResponse.builder().token(token).build();
    }

    @Override
    public LoginResponse oauthLogin(OAuthLoginRequest request) {
        String email = request.getEmail();
        MemberRole role = MemberRole.valueOf(request.getRole());

        String refresh = createRefreshToken(email, role);
        
        return LoginResponse.builder().token(refresh).build();
    };

    private boolean isMatchedPassword(String input, Member member) {
        return passwordEncoder.matches(input, member.getPassword());
    }

    public String createRefreshToken(String username, MemberRole role) { return ""; };
    
    @Override
    public String createAccessToken(String refresh) { return "" ;};

    // 토큰 동작
    private boolean validToken(String token) {
        return jwtProvider.validateToken(token);
    };

    public String createToken(String email, MemberRole role, TokenType type) {
        return jwtProvider.createToken(email, role, type);
    }

}
