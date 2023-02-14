package com.sparta.soomtut.repository;

import com.sparta.soomtut.entity.FavMemberPost;
import com.sparta.soomtut.entity.Member;
import com.sparta.soomtut.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavMemberPostRepository extends JpaRepository<FavMemberPost,Long> {
    Optional<FavMemberPost> findByPostAndMember(Post post, Member member);
    boolean existsByPostIdAndMember(Long id, Member member);

}
