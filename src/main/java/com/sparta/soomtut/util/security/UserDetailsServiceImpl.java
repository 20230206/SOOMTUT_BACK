package com.sparta.soomtut.util.security;

import com.sparta.soomtut.member.entity.Member;
import com.sparta.soomtut.member.service.MemberService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberService memberService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Member member = memberService.getMemberByEmail(email);
		return new UserDetailsImpl(member);
    }

}


