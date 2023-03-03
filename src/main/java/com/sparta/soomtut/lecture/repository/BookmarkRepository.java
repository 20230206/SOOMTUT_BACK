package com.sparta.soomtut.lecture.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.soomtut.lecture.entity.Bookmark;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Optional<Bookmark> findByPostIdAndMemberId(Long postId, Long memberId);
    boolean existsByPostIdAndMemberId(Long postId, Long memberId);
    Optional<Bookmark> findByPostId(Long postId);
    Page<Bookmark> findAllByMemberIdAndStatusIsTrue(Long memberId, Pageable pageable);

}
