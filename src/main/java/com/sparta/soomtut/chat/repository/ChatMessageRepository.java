package com.sparta.soomtut.chat.repository;

import com.sparta.soomtut.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByRoomId(Long roomId);
    Optional<ChatMessage> findByRoomIdOrderBySentAtDesc(Long roomId);

}
