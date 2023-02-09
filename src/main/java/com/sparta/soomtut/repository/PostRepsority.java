package com.sparta.soomtut.repository;

import com.sparta.soomtut.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepsority extends JpaRepository <Post, Long> {
}
