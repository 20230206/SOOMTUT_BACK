package com.sparta.soomtut.auth.service.impl;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.soomtut.auth.dto.request.LoginRequest;
import com.sparta.soomtut.auth.dto.request.OAuthLocationRequest;
import com.sparta.soomtut.auth.dto.request.OAuthLoginRequest;
import com.sparta.soomtut.auth.dto.request.RegisterRequest;
import com.sparta.soomtut.auth.dto.response.LoginResponse;
import com.sparta.soomtut.auth.entity.Auth;
import com.sparta.soomtut.auth.repository.AuthRepository;
import com.sparta.soomtut.auth.service.AuthService;
import com.sparta.soomtut.entity.Location;
import com.sparta.soomtut.member.dto.response.MemberInfoResponse;
import com.sparta.soomtut.member.entity.Member;
import com.sparta.soomtut.member.service.MemberService;
import com.sparta.soomtut.service.interfaces.LocationService;
import com.sparta.soomtut.util.enums.MemberRole;
import com.sparta.soomtut.util.jwt.JwtProvider;
import com.sparta.soomtut.util.jwt.TokenType;
import com.sparta.soomtut.util.response.ErrorCode;

import  com.sparta.soomtut.util.exception.CustomException;


import lombok.RequiredArgsConstructor;

// lombok
@RequiredArgsConstructor

// jpa
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;

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

        String token = createRefreshToken(member.getEmail(), member.getMemberRole());
        
        return LoginResponse.builder().token(token).build();
    }

    @Override
    @Transactional
    public LoginResponse oauthLogin(OAuthLoginRequest request) {
        String email = request.getEmail();
        int hash = request.getHash();

        Auth auth = isValidOAuthLoginRequest(email, hash);
        MemberRole role = MemberRole.valueOf(request.getRole());
        authRepository.delete(auth);

        String refresh = createRefreshToken(email, role);

        
        return LoginResponse.builder().token(refresh).build();
    };

    private boolean isMatchedPassword(String input, Member member) {
        return passwordEncoder.matches(input, member.getPassword());
    }

    private Auth isValidOAuthLoginRequest(String email, int hash) {
        return authRepository.findByEmailAndHash(email, hash).orElseThrow(
            () -> new CustomException(ErrorCode.LOGIN_FAILED)
        );
    }

    public String createRefreshToken(String username, MemberRole role) {
        return jwtProvider.createToken(username, role, TokenType.REFRESH);
    }
    
    @Override
    public String createAccessToken(String refresh) { 
        if(!validToken(refresh)) throw new CustomException(ErrorCode.INVALID_TOKEN);

        return jwtProvider.createToken(refresh) ;
    };

    @Override
    @Transactional
    public MemberInfoResponse updateOAuthInfo(OAuthLocationRequest request, String refresh) {
        if(!validToken(refresh)) throw new CustomException(ErrorCode.INVALID_TOKEN);

        String email = getEmailFromToken(refresh);
        Member member = memberService.getMemberByEmail(email);
        Location location = locationService.findMemberLocation(member.getId());
        location.updateAddress(request.getAddress());
        member.changeState(true);

        return MemberInfoResponse.toDto(member, location);
    }

    // 토큰 동작
    private boolean validToken(String token) {
        return jwtProvider.validateToken(token);
    }

    public String createToken(String email, MemberRole role, TokenType type) {
        return jwtProvider.createToken(email, role, type);
    }

    private String getEmailFromToken(String token) {
        return jwtProvider.getEmail(token);
    }

    // repository 지원 함수
    public void saveAuth(Auth auth) {
        authRepository.save(auth);
    }
}
