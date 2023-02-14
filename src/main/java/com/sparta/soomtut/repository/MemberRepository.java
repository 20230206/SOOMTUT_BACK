package com.sparta.soomtut.repository;

import com.sparta.soomtut.entity.Member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
    
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);


}
