package com.sparta.soomtut.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import com.sparta.soomtut.auth.dto.request.LoginRequest;
import com.sparta.soomtut.auth.dto.request.OAuthInitRequest;
import com.sparta.soomtut.auth.dto.request.OAuthLoginRequest;
import com.sparta.soomtut.auth.dto.request.RegisterRequest;
import com.sparta.soomtut.auth.dto.response.LoginResponse;
import com.sparta.soomtut.auth.entity.Auth;
import com.sparta.soomtut.auth.repository.AuthRepository;
import com.sparta.soomtut.auth.service.impl.AuthServiceImpl;
import com.sparta.soomtut.member.dto.response.MemberResponse;
import com.sparta.soomtut.member.entity.Member;
import com.sparta.soomtut.member.entity.enums.MemberRole;
import com.sparta.soomtut.member.entity.enums.MemberState;
import com.sparta.soomtut.member.repository.MemberRepository;
import com.sparta.soomtut.member.service.impl.MemberServiceImpl;
import com.sparta.soomtut.location.entity.Location;
import com.sparta.soomtut.location.dto.request.*;
import com.sparta.soomtut.location.service.LocationService;
import com.sparta.soomtut.util.exception.CustomException;
import com.sparta.soomtut.util.jwt.JwtProvider;
import com.sparta.soomtut.util.jwt.TokenType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {
    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private MemberServiceImpl memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private LocationService locationService;

    @Mock
    private AuthRepository authRepository;

    @Spy
    private BCryptPasswordEncoder passwordEncoder;

    @Spy
    private JwtProvider jwtProvider;

    
    @BeforeEach
    void prepare() {
        ReflectionTestUtils.setField(jwtProvider,
                "secretKey", // jwtUtil??? secretKey?????? ????????? ??????
                "7ZWt7ZW0OTntmZTsnbTtjIXtlZzqta3snYTrhIjrqLjshLjqs4TroZzrgpjslYTqsIDsnpDtm4zrpa3tlZzqsJzrsJzsnpDrpbzrp4zrk6TslrTqsIDsnpA="); // secretKey??? ???
            jwtProvider.init(); // jwtUtil?????? @PostConstructor??? ???????????? ?????? ?????????, ????????? ??????????????? ???
    }

    @Test
    @DisplayName("?????? ??????(??????)")
    void register() {
        // given
        RegisterRequest request = RegisterRequest.builder()
            .nickname("??????????????????")
            .email("user@user.com")
            .password("1q2w3e4r!")
            .address("??????????????? ????????? ?????????")
            .posX(37.5049f)
            .posY(127.024f)
            .sido("???????????????")
            .sigungu("?????????")
            .bname("?????????")
            .build();

        LocationRequest locationRequest = LocationRequest.registerConvert().request(request).build();
        Location location = Location.builder().request(locationRequest).build();

        Member member = Member.builder().request(request).location(location).build();

        // when
        when(memberService.existsMemberByEmail(any(String.class))).thenReturn(false);
        when(memberService.existsMemberByNickname(any(String.class))).thenReturn(false);
        when(memberService.saveMember(any(Member.class))).thenReturn(member);
        when(locationService.saveLocation(any(LocationRequest.class))).thenReturn(location);

        MemberResponse res = authService.register(request);

        // then
        assertEquals(member.getEmail(), res.getEmail());
        assertEquals(member.getNickname(), res.getNickname());
        assertEquals(member.getLocation().getAddress(), res.getLocation().getAddress());
        assertEquals(member.getLocation().getPosX(), res.getLocation().getPosX());
        assertEquals(member.getLocation().getPosY(), res.getLocation().getPosY());
        assertEquals(member.getLocation().getBname(), res.getLocation().getBname());
        assertEquals(member.getLocation().getSido(), res.getLocation().getSido());
        assertEquals(member.getLocation().getSigungu(), res.getLocation().getSigungu());
        assertEquals(MemberState.ACTIVE , res.getState());
    }
    
    @Test
    @DisplayName("?????? ??????(??????)-???????????????")
    void registerExceptionDupleEmail() {
        // given
        RegisterRequest request = RegisterRequest.builder()
            .nickname("??????????????????")
            .email("user@user.com")
            .password("1q2w3e4r!")
            .address("??????????????? ????????? ?????????")
            .posX(37.5049f)
            .posY(127.024f)
            .sido("???????????????")
            .sigungu("?????????")
            .bname("?????????")
            .build();

        // when
        when(memberService.existsMemberByEmail(any(String.class))).thenReturn(true);
        
        // then
        assertThrows(CustomException.class, () -> {
            authService.register(request);
        });
    }

    @Test
    @DisplayName("?????? ??????(??????)-???????????????")
    void registerExceptionDupleNickname() {
        // given
        RegisterRequest request = RegisterRequest.builder()
            .nickname("??????????????????")
            .email("user@user.com")
            .password("1q2w3e4r!")
            .address("??????????????? ????????? ?????????")
            .posX(37.5049f)
            .posY(127.024f)
            .sido("???????????????")
            .sigungu("?????????")
            .bname("?????????")
            .build();

        // when
        when(memberService.existsMemberByEmail(any(String.class))).thenReturn(false);
        when(memberService.existsMemberByNickname(any(String.class))).thenReturn(true);

        // then
        assertThrows(CustomException.class, () -> {
            authService.register(request);
        });
    }

    @Test
    @DisplayName("????????? (??????)")
    void login() {
        // given
        RegisterRequest registerReq = RegisterRequest.builder()
        .nickname("????????? ??????")
        .email("user@user.com")
        .password(passwordEncoder.encode("1q2w3e4r!"))
        .build();

        LoginRequest loginReq = LoginRequest.builder()
            .email("user@user.com")
            .password("1q2w3e4r!")
            .build();

        Member member = Member.builder().request(registerReq).build();
        member.updatePassword(registerReq.getPassword());
        member.changeState(MemberState.ACTIVE);

        // when
        when(memberService.getMemberByEmail(any(String.class))).thenReturn(member);
        LoginResponse res = authService.login(loginReq);

        // then
        verify(jwtProvider, times(1))
                .createToken(member.getEmail(), MemberRole.MEMBER, TokenType.REFRESH);
        assertEquals(member.getState(), res.getState());
    }

    @Test
    @DisplayName("????????? (??????)-???????????? ??????")
    void loginFail() {
        // given
        RegisterRequest registerReq = RegisterRequest.builder()
        .nickname("????????? ??????")
        .email("user@user.com")
        .password(passwordEncoder.encode("1q2w3e4r!"))
        .build();

        LoginRequest loginReq = LoginRequest.builder()
            .email("user@user.com")
            .password("asdfasdf!")
            .build();

        Member member = Member.builder().request(registerReq).build();
        member.updatePassword(registerReq.getPassword());
        member.changeState(MemberState.ACTIVE);

        // when
        when(memberService.getMemberByEmail(any(String.class))).thenReturn(member);

        // then
        assertThrows(CustomException.class, () -> {
            authService.login(loginReq);
        });
    }

    @Test
    @DisplayName("OAuth2 ?????????(??????)")
    void oauthLogin() {
        // given
        OAuthLoginRequest loginRequest = OAuthLoginRequest.builder()
                    .email("user@user.com")
                    .role(MemberRole.MEMBER.toString())
                    .hash(1234)
                    .build();

        Auth auth = new Auth(1L, "user@user.com", 1234);
        
        when(authRepository.findByEmailAndHash(any(String.class),
            any(Integer.class).intValue())).thenReturn(Optional.of(auth));

        // when
        authService.oauthLogin(loginRequest);

        // then
        verify(jwtProvider, times(1)).createToken(loginRequest.getEmail(), MemberRole.MEMBER, TokenType.REFRESH);
    }

    @Test
    @DisplayName("OAuth2 ?????????(??????)-???????????? ??????")
    void oauthLoginFail() {
        // given
        OAuthLoginRequest loginRequest = OAuthLoginRequest.builder()
                    .email("user@user.com")
                    .role(MemberRole.MEMBER.toString())
                    .hash(1234)
                    .build();
        
        assertThrows(CustomException.class, () -> {
            authService.oauthLogin(loginRequest);
        });
    }

    @Test 
    @DisplayName("OAuth2 ?????? ????????? ?????? ?????? ??????")
    void updateOAuthInfo() {
        // given
        RegisterRequest memberRequest = RegisterRequest.builder()
                .nickname("??????????????????")
                .email("user@user.com")
                .password("1q2w3e4r!")
                .build();

        Member member = Member.builder().request(memberRequest).location(new Location()).build();

        OAuthInitRequest oAuthRequest = OAuthInitRequest.builder()
                .nickname("OAuth????????????")
                .address("??????????????? ????????? ?????????")
                .posX(37.5049f)
                .posY(127.024f)
                .sido("???????????????")
                .sigungu("?????????")
                .bname("?????????")
                .build();

        LocationRequest locationRequest = LocationRequest.oauthInitconvert().request(oAuthRequest).build();
        locationService.updateLocation(member.getLocation().getId(), locationRequest);

        String token = jwtProvider.createToken(oAuthRequest.getNickname(), MemberRole.MEMBER, TokenType.REFRESH);

        member.updateNickName(oAuthRequest.getNickname());
        member.changeState(MemberState.ACTIVE);

        when(jwtProvider.getEmail(token)).thenReturn(member.getEmail());
        when(memberService.getMemberByEmail(any(String.class))).thenReturn(member);
        when(jwtProvider.validateToken(any(String.class))).thenReturn(true);

        MemberResponse res = authService.updateOAuthInfo(oAuthRequest, token);

        assertEquals(member.getEmail(), res.getEmail());
        assertEquals(member.getNickname(), res.getNickname());
        assertEquals(member.getLocation().getAddress(), res.getLocation().getAddress());
        assertEquals(member.getLocation().getPosX(), res.getLocation().getPosX());
        assertEquals(member.getLocation().getPosY(), res.getLocation().getPosY());
        assertEquals(member.getLocation().getBname(), res.getLocation().getBname());
        assertEquals(member.getLocation().getSido(), res.getLocation().getSido());
        assertEquals(member.getLocation().getSigungu(), res.getLocation().getSigungu());
        assertEquals(MemberState.ACTIVE , res.getState());
    }
}
