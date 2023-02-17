package com.sparta.soomtut.repository;

import com.sparta.soomtut.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;


import java.awt.print.Pageable;
import java.util.Optional;
import java.util.List;

public interface PostRepository extends JpaRepository <Post, Long> {
    Optional<Post>findById(Long postId);
    
    List<Post> findAllByTutorId(Long tutorId);
    Optional<Post> findByTutorId(Long tutorId);
    List<Post> findAll(Pageable pageable);

    List<Post> findAllByMemberId(Long memberId);
    Optional<Post> findByMemberId(Long memberId);
    List<Post> findAllByCategoryId(Long categoryId);
}
