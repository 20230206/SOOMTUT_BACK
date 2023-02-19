package com.sparta.soomtut.repository;

import com.sparta.soomtut.entity.FavMemberPost;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavMemberPostRepository extends JpaRepository<FavMemberPost, Long> {
    Optional<FavMemberPost> findByPostIdAndMemberId(Long postId, Long memberId);
    boolean existsByPostIdAndMemberId(Long postId, Long memberId);

    Optional<FavMemberPost> findByPostId(Long postId);
    Page<FavMemberPost> findAllByMemberIdAndStatusIsTrue(Long memberId, Pageable pageable);

    //TODO: 계속해서 오류가 나와서 주석처리함 수정 필요
    // Page<FavMemberPost> findAllByMemberId(Pageable pageable);

}
