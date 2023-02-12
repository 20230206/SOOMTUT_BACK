package com.sparta.soomtut.service.impl;

import com.sparta.soomtut.entity.Location;
import com.sparta.soomtut.entity.Member;
import com.sparta.soomtut.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @Mock
    MemberRepository memberRepository;

    @Mock
    LocationServiceImpl locationService;

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

    @Test
    @DisplayName("닉네임 가져오기")
    void getNickname(){
        Member member = new Member("user@user.com","asd12345","user1");
        given(memberRepository.findById(member.getId())).willReturn(Optional.ofNullable(member));
        //given(memberService.findMemberById(member.getId())).willReturn(member);

        assertThat(memberService.findMemberById(member.getId()).getNickname()).isEqualTo(member.getNickname());

    }

    @Test
    @DisplayName("위치정보 가져오기")
    void getLocation(){
        Member member = new Member("user@user.com","asd12345","user1");
        Location location = new Location(1L,member,"서울",3f,3f);
        given(memberRepository.findById(member.getId())).willReturn(Optional.ofNullable(member));
        //given(memberService.findMemberById(member.getId())).willReturn(member);
        given(locationService.getLocation(member)).willReturn(location);
        //given(locationRepository.findByMemberId(anyLong())).willReturn(location);
        //given(locationRepository.findByMemberId(member.getId())).willReturn(location);
        String address = memberService.getLocation(member);
        assertThat(address).isEqualTo("서울");
        //verify(locationRepository,times(1)).findByMemberId(anyLong());

    }

    @Test
    @DisplayName("가입일자 가져오기")
    void getSignupDate(){
        Member member = new Member("user@user.com","asd12345","user1");
        given(memberRepository.findById(member.getId())).willReturn(Optional.ofNullable(member));
        LocalDate signupDate = memberService.getSignupDate(member);

        assertThat(signupDate).isEqualTo(member.getCreatedAt());

    }

    @Test
    @DisplayName("레벨 가져오기")
    void getLevel(){
        Member member = new Member("user@user.com","asd12345","user1");
        given(memberRepository.findById(member.getId())).willReturn(Optional.ofNullable(member));
        int level = memberService.getLevel(member);
        assertThat(level).isEqualTo(0);

    }

    @Test
    @DisplayName("이미지 가져오기")
    void getImage(){
        Member member = new Member("user@user.com","asd12345","user1");
        given(memberRepository.findById(member.getId())).willReturn(Optional.ofNullable(member));
        String image = memberService.getImage(member);
        assertThat(image).isEqualTo(member.getImage());

    }







}