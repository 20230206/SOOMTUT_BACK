package com.sparta.soomtut.service.impl;

import com.sparta.soomtut.entity.Member;
import com.sparta.soomtut.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    MemberServiceImpl memberService;

    @Test
    @DisplayName("유저 닉네임 업데이트(성공)")
    void updateNickname() {

        Member member = new Member("user@user.com","asd12345","user1");
        given(memberRepository.findById(any())).willReturn(Optional.ofNullable(member));

        String msg = memberService.updateNickname("new nickname",member);

        assertThat(msg).isEqualTo("수정이 완료되었습니다!");
        verify(memberRepository,times(1)).findById(member.getId());
        assertThat(member.getNickname()).isEqualTo("new nickname");

    }
}