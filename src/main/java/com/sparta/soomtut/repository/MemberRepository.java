package com.sparta.soomtut.repository;

import com.sparta.soomtut.entity.Member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
    Optional<Member> findByProviderAndOauthEmail(String provider, String oauthEmail);
    Optional<Member> findById(Long memberId);
    Optional<Member> findByNickname(String nickname);


    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);


}
