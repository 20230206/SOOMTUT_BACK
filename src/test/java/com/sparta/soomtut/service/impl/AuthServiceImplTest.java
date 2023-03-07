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
import com.sparta.soomtut.member.repository.MemberRepository;
import com.sparta.soomtut.member.service.impl.MemberServiceImpl;
import com.sparta.soomtut.location.entity.Location;
import com.sparta.soomtut.location.dto.request.*;
import com.sparta.soomtut.location.repository.LocationRepository;
import com.sparta.soomtut.location.service.impl.LocationServiceImpl;
import com.sparta.soomtut.util.jwt.JwtProvider;

import static org.assertj.core.api.Assertions.assertThat;
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
    private LocationServiceImpl locationService;

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
    void signup() {
        // given
        // RegisterRequest request = RegisterRequest.builder()
        //     .nickname("SignupSuccess")
        //     .email("user@user.com")
        //     .password("1q2w3e4r!")
        //     .address("서울특별시 서초구 반포동")
        //     .posX(0)
        //     .posY(0)
        //     .sido("서울특별시")
        //     .sigungu("서초구")
        //     .bname("반포동")
        //     .build();

        // LocationRequest locationRequest = LocationRequest.registerConvert().request(request).build();
        // Location location = Location.builder().request(locationRequest).build();

        // Member member = Member.builder().request(request).location(location).build();


        // when(memberService.saveMember(any(Member.class))).thenReturn(member);
        // when(memberService.existsMemberByEmail(any(String.class))).thenReturn(false);
        // when(memberService.existsMemberByNickname(any(String.class))).thenReturn(false);
        // when(locationService.saveLocation(location)).thenReturn(location);

        // // when
        // MemberResponse res = authService.register(request);

        // then
        // Hibernate: 
        //     insert
        //     into
        //         member
        //         (created_at, email, image, level, member_role, nickname, password, star_rating)
        //     values
        //         (?, ?, ?, ?, ?, ?, ?, ?)
    }

    @Test
    @DisplayName("로그인 (성공)")
    void signin() {
        // given
        LoginRequest requestDto = LoginRequest.builder()
            .email("user@user.com")
            .password("1q2w3e4r!")
            .build();

        // Member member = new Member("user@user.com", passwordEncoder.encode("1q2w3e4r!"), "LoginTest");

        // when(memberService.getMemberByEmail(any(String.class))).thenReturn(member);
        // 패스워드 우회?

        // when
        // LoginResponse res = authService.login(requestDto);

        // then
        // verify(jwtProvider).createToken(member.getEmail(), member.getMemberRole(), TokeType.);
    }
}
