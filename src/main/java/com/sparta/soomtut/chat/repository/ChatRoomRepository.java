package com.sparta.soomtut.chat.repository;

import com.sparta.soomtut.chat.entity.ChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    boolean existsByTuteeIdAnAndTutorId(Long tuteeId, Long tutorId);
    Page<ChatRoom> findAllByTuteeIdAndTutorId(Long tuteeId, Long tutorId, Pageable pageable);

}
