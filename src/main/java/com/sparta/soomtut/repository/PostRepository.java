package com.sparta.soomtut.repository;

import com.sparta.soomtut.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository <Post, Long> {

    List<Post> findAllByTutorId(Long tutorId);


    Optional<Post> findByTutorId(Long tutorId);

}
