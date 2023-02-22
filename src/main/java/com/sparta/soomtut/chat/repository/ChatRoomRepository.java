package com.sparta.soomtut.chat.repository;

import com.sparta.soomtut.chat.entity.ChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    boolean existsBySenderIdAndRecipientId(Long senderId, Long recipientId);

    Page<ChatRoom> findAllBySenderIdAndRecipientId(Long senderId, Long recipientId, Pageable pageable);

}
