package com.sparta.soomtut.chat.dto;

import com.sparta.soomtut.chat.entity.ChatRoom;
import com.sparta.soomtut.dto.response.MemberInfoResponse;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ChatRoomResponse {

    private Long id;
    private MemberInfoResponse member1;

    private MemberInfoResponse member2;

    private List<ChatResponseDto> chats;

    private LocalDateTime createdAt;
    private ChatRoomResponse(Long id, MemberInfoResponse member1, MemberInfoResponse member2, List<ChatResponseDto> chats, LocalDateTime createdAt ) {
        this.id = id;
        this.member1 = member1;
        this.member2 = member2;
        this.chats = chats;
        this.createdAt = createdAt;
    }

    public static ChatRoomResponse of(ChatRoom chatRoom,MemberInfoResponse member1, MemberInfoResponse member2, List<ChatResponseDto> chats ){
        return new ChatRoomResponse(
                chatRoom.getId(),
                member1,
                member2,
                chats,
                chatRoom.getCreatedAt()
        );
    }
}
