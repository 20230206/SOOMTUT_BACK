package com.sparta.soomtut.chat.repository;

import com.sparta.soomtut.chat.entity.ChatRoom;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    boolean existsByTuteeIdAndTutorId(Long tuteeId, Long tutorId);
    Page<ChatRoom> findAllByTuteeIdAndTutorId(Long tuteeId, Long tutorId, Pageable pageable);
    Optional<ChatRoom> findByTuteeIdAndPostId(Long tuteeId, Long postId);

}
