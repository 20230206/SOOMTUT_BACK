package com.sparta.soomtut.chat.service;

import com.sparta.soomtut.chat.dto.ChatRoomResponse;
import com.sparta.soomtut.chat.entity.ChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChatRoomService {
    void createRoom(Long tuteeId, Long postId);

    ChatRoomResponse getMyChatRoom(Long roomId);
    Page<ChatRoomResponse> getMyChatRooms(Long memberId, Pageable pageable);

    Page<ChatRoom> getAllMyChatRooms(Long memberId, Pageable pageable);

}
