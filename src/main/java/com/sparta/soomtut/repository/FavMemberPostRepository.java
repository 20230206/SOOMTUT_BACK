package com.sparta.soomtut.repository;

import com.sparta.soomtut.entity.FavMemberPost;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavMemberPostRepository extends JpaRepository<FavMemberPost, Long> {
    Optional<FavMemberPost> findByPostIdAndMemberId(Long postId, Long memberId);
    boolean existsByPostIdAndMemberId(Long postId, Long memberId);

    List<FavMemberPost> findAllByMemberId(Long memberId);

    Optional<FavMemberPost> findByPostId(Long postId);

}
