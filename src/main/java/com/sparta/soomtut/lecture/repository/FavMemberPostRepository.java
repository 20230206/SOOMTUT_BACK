package com.sparta.soomtut.lecture.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.soomtut.lecture.entity.FavMemberPost;

import java.util.Optional;

public interface FavMemberPostRepository extends JpaRepository<FavMemberPost, Long> {
    Optional<FavMemberPost> findByPostIdAndMemberId(Long postId, Long memberId);
    boolean existsByPostIdAndMemberId(Long postId, Long memberId);

    Optional<FavMemberPost> findByPostId(Long postId);
    Page<FavMemberPost> findAllByMemberIdAndStatusIsTrue(Long memberId, Pageable pageable);

}
