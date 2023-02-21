package com.sparta.soomtut.repository;

import com.sparta.soomtut.entity.Post;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;
import java.util.List;

public interface PostRepository extends JpaRepository <Post, Long> {
    Optional<Post>findById(Long postId);
    List<Post> findAllByTutorId(Long tutorId);
    Optional<Post> findByTutorId(Long tutorId);
    
    Page<Post> findAll(Pageable pageable);
    List<Post> findAllById(Long id);


}
