package com.sparta.soomtut.chat.repository;

import com.sparta.soomtut.chat.entity.ChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    boolean existsByMember1IdAndMember2Id(Long member1id, Long member2id);
    Page<ChatRoom> findAllByMember1IdAndMember2Id(Long member1id, Long member2id, Pageable pageable);

}
