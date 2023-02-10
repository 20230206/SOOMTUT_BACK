package com.sparta.soomtut.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sparta.soomtut.dto.SignupRequestDto;
import com.sparta.soomtut.repository.MemberRepository;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Spy
    private BCryptPasswordEncoder passwordEncoder;
    

    @Test
    @DisplayName("회원 가입(성공)")
    void signup() {
        // given
        SignupRequestDto requestDto = SignupRequestDto.builder()
            .nickname("SignupSuccess")
            .email("user@user.com")
            .password("1q2w3e4r!")
            .build();

        when(memberService.existsMemberByEmail(any(String.class))).thenReturn(false);
        when(memberService.existsMemberByNickname(any(String.class))).thenReturn(false);

        // when
        authService.signup(requestDto);

        // then
        // Hibernate: 
        //     insert
        //     into
        //         member
        //         (created_at, email, image, level, member_role, nickname, password, star_rating)
        //     values
        //         (?, ?, ?, ?, ?, ?, ?, ?)
    }
}
