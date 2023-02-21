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
    public MemberInfoResponseDto register(SignupRequestDto requestDto) {
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

        return MemberInfoResponseDto.toDto(member, location);
    }

    @Override
    @Transactional
    public SigninResponseDto login(SigninRequestDto requestDto) {
        String email = requestDto.getEmail();      
        String password = requestDto.getPassword();
        
        Member member = memberService.getMemberByEmail(email);

        if(!isMatchedPassword(password, member)) 
            throw new CustomException(ErrorCode.INVALID_PASSWORD);

        if (!member.isState()) 
            throw new CustomException(ErrorCode.SECESSION_USER);

        String token = createToken(member.getEmail(), member.getMemberRole(), TokenType.REFRESH);
        
        return SigninResponseDto.builder().token(token).build();
    }

    private boolean isMatchedPassword(String input, Member member) {
        return passwordEncoder.matches(input, member.getPassword());
    }

    @Override
    public boolean checkToken(String token) {
        return jwtProvider.validateToken(token);
    };

    @Override
    public String createToken(String token) {
        boolean isValid = this.checkToken(token);

        if(!isValid) throw new CustomException(ErrorCode.INVALID_TOKEN);
        
        Claims claims = jwtProvider.getUserInfoFromToken(token);

        String username = claims.getSubject();

        String memberValue = (String) claims.get(JwtProvider.AUTHORIZATION_KEY).toString();
        MemberRole role = MemberRole.valueOf(memberValue);

        String typeValue = (String) claims.get(JwtProvider.TOKEN_TYPE).toString();
        TokenType type = TokenType.valueOf(typeValue);

        if(TokenType.OAUTH2.equals(type)) {
            return createToken(username, role, TokenType.REFRESH);
        }
        else if (TokenType.REFRESH.equals(type)) {
            return createToken(username, role, TokenType.ACCESS);
        }
        else {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }

    public String createToken(String email, MemberRole role, TokenType type) {
        return jwtProvider.createToken(email, role, type);
    }
}
