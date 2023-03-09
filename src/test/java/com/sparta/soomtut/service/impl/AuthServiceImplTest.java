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
import com.sparta.soomtut.auth.dto.request.RegisterRequest;
import com.sparta.soomtut.auth.dto.response.LoginResponse;
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

    @Spy
    private BCryptPasswordEncoder passwordEncoder;

    @Spy
    private JwtProvider jwtProvider;

    
    @BeforeEach
    void prepare() {
        ReflectionTestUtils.setField(jwtProvider,
                "secretKey", // jwtUtil의 secretKey값이 저장될 변수
                "7ZWt7ZW0OTntmZTsnbTtjIXtlZzqta3snYTrhIjrqLjshLjqs4TroZzrgpjslYTqsIDsnpDtm4zrpa3tlZzqsJzrsJzsnpDrpbzrp4zrk6TslrTqsIDsnpA="); // secretKey의 값
            jwtProvider.init(); // jwtUtil에서 @PostConstructor가 동작하지 않기 때문에, 임의로 실행시켜야 함
    }

    @Test
    @DisplayName("회원 가입(성공)")
    void register() {
        // given
        RegisterRequest request = RegisterRequest.builder()
            .nickname("회원가입성공")
            .email("user@user.com")
            .password("1q2w3e4r!")
            .address("서울특별시 서초구 반포동")
            .posX(37.5049f)
            .posY(127.024f)
            .sido("서울특별시")
            .sigungu("서초구")
            .bname("반포동")
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
    @DisplayName("회원 가입(실패)-중복이메일")
    void registerExceptionDupleEmail() {
        // given
        RegisterRequest request = RegisterRequest.builder()
            .nickname("회원가입성공")
            .email("user@user.com")
            .password("1q2w3e4r!")
            .address("서울특별시 서초구 반포동")
            .posX(37.5049f)
            .posY(127.024f)
            .sido("서울특별시")
            .sigungu("서초구")
            .bname("반포동")
            .build();

        // when
        when(memberService.existsMemberByEmail(any(String.class))).thenReturn(true);
        
        // then
        assertThrows(CustomException.class, () -> {
            authService.register(request);
        });
    }

    @Test
    @DisplayName("회원 가입(실패)-중복닉네임")
    void registerExceptionDupleNickname() {
        // given
        RegisterRequest request = RegisterRequest.builder()
            .nickname("회원가입성공")
            .email("user@user.com")
            .password("1q2w3e4r!")
            .address("서울특별시 서초구 반포동")
            .posX(37.5049f)
            .posY(127.024f)
            .sido("서울특별시")
            .sigungu("서초구")
            .bname("반포동")
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
    @DisplayName("로그인 (성공)")
    void login() {
        // given
        RegisterRequest registerReq = RegisterRequest.builder()
        .nickname("로그인 성공")
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
    @DisplayName("로그인 (실패)-비밀번호 틀림")
    void loginFail() {
        // given
        RegisterRequest registerReq = RegisterRequest.builder()
        .nickname("로그인 실패")
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
}
