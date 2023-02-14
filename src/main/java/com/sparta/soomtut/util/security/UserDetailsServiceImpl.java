package com.sparta.soomtut.util.security;

import com.sparta.soomtut.entity.Member;
import com.sparta.soomtut.exception.ErrorCode;
import com.sparta.soomtut.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(
				() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_TOKEN.getMessage()));
		return new UserDetailsImpl(member);
    }

}


