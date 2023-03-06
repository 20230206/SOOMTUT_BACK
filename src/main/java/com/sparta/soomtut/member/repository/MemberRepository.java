package com.sparta.soomtut.member.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.soomtut.member.entity.Member;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
    Optional<Member> findByProviderAndOauthEmail(String provider, String oauthEmail);
    Optional<Member> findById(Long memberId);
    Optional<Member> findByNickname(String nickname);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

    @Query("select m,l from Member m join Location l on m.location.id=l.id Where m.location.sido=:sido AND m.id!=:memberId")
    List<Member> findAllByAddress(@Param("sido") String sido,@Param("memberId") Long memberId);

}
