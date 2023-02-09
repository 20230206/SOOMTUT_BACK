package com.sparta.soomtut.repository;

import com.sparta.soomtut.entity.Member;
import com.sparta.soomtut.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
