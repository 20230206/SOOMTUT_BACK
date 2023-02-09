package com.sparta.soomtut.repository;

import com.sparta.soomtut.entity.ChatRoom;
import com.sparta.soomtut.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
