package com.sparta.soomtut.auth.service.impl;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.soomtut.auth.dto.request.LoginRequest;
import com.sparta.soomtut.auth.dto.request.OAuthInitRequest;
import com.sparta.soomtut.auth.dto.request.OAuthLoginRequest;
import com.sparta.soomtut.auth.dto.request.RegisterRequest;
import com.sparta.soomtut.auth.dto.response.LoginResponse;
import com.sparta.soomtut.auth.entity.Auth;
import com.sparta.soomtut.auth.repository.AuthRepository;
import com.sparta.soomtut.auth.service.AuthService;
import com.sparta.soomtut.member.dto.response.MemberResponse;
import com.sparta.soomtut.member.entity.Member;
import com.sparta.soomtut.member.entity.enums.MemberRole;
import com.sparta.soomtut.member.entity.enums.MemberState;
import com.sparta.soomtut.member.service.MemberService;
import com.sparta.soomtut.location.dto.request.LocationRequest;
import com.sparta.soomtut.location.entity.Location;
import com.sparta.soomtut.location.service.LocationService;
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
    public MemberResponse register(RegisterRequest request) {
        String email = request.getEmail();
        String password = passwordEncoder.encode(request.getPassword());
        String nickname = request.getNickname();

        if(memberService.existsMemberByEmail(email))
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
        
        if(memberService.existsMemberByNickname(nickname))
            throw new CustomException(ErrorCode.DUPLICATED_NICKNAME);

        LocationRequest locationRequest = LocationRequest.registerConvert().request(request).build();
        Location location = locationService.saveLocation(locationRequest);
        
        Member member = Member.builder().request(request).location(location).build();
        member.updatePassword(password);
        member.changeState(MemberState.ACTIVE);

        memberService.saveMember(member);
        
        return MemberResponse.toDto().member(member).build();
    }

    @Override
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest requestDto) {
        String email = requestDto.getEmail();      
        String password = requestDto.getPassword();
        Member member = memberService.getMemberByEmail(email);

        if(!isMatchedPassword(password, member))
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        String token = createRefreshToken(member.getEmail(), member.getMemberRole());
        return LoginResponse.builder().state(member.getState()).token(token).build();
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
    }

    private boolean isMatchedPassword(String input, Member member) {
        return passwordEncoder.matches(input, member.getPassword());
    }


    private Auth isValidOAuthLoginRequest(String email, int hash) {
        return authRepository.findByEmailAndHash(email, hash).orElseThrow(
            () -> new CustomException(ErrorCode.LOGIN_FAILED));
    }

    @Transactional
    public String createRefreshToken(String username, MemberRole role) {
        return jwtProvider.createToken(username, role, TokenType.REFRESH);
    }
    
    @Override
    @Transactional
    public String createAccessToken(String refresh) { 
        if(!validToken(refresh)) throw new CustomException(ErrorCode.INVALID_TOKEN);
        return jwtProvider.createToken(refresh) ;
    }

    @Override
    @Transactional
    public MemberResponse updateOAuthInfo(OAuthInitRequest request, String refresh) {
        if(!validToken(refresh)) throw new CustomException(ErrorCode.INVALID_TOKEN);

        String email = getEmailFromToken(refresh);
        Member member = memberService.getMemberByEmail(email);

        LocationRequest locationRequest = LocationRequest.oauthInitconvert().request(request).build();
        locationService.updateLocation(member.getLocation().getId(), locationRequest);

        member.changeState(MemberState.ACTIVE);
        return MemberResponse.toDto().member(member).build();
    }

    // 토큰 동작
    private boolean validToken(String token) {
        return jwtProvider.validateToken(token);
    }

    private String getEmailFromToken(String token) {
        return jwtProvider.getEmail(token);
    }

    // repository 지원 함수
    @Transactional
    public void saveAuth(Auth auth) {
        authRepository.save(auth);
    }
}
