package com.sparta.soomtut.util.security;

import com.sparta.soomtut.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsService {

    private final MemberRepository memberRepository;





}


