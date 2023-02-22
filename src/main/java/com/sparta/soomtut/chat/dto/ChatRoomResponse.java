package com.sparta.soomtut.chat.dto;

import com.sparta.soomtut.chat.entity.ChatRoom;
import com.sparta.soomtut.dto.response.MemberInfoResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ChatRoomResponse {

    private Long id;
    private MemberInfoResponseDto member1;

    private MemberInfoResponseDto member2;

    private List<ChatResponseDto> chats;

    private LocalDateTime createdAt;
    private ChatRoomResponse(Long id, MemberInfoResponseDto member1, MemberInfoResponseDto member2, List<ChatResponseDto> chats, LocalDateTime createdAt ) {
        this.id = id;
        this.member1 = member1;
        this.member2 = member2;
        this.chats = chats;
        this.createdAt = createdAt;
    }

    public static ChatRoomResponse of(ChatRoom chatRoom,MemberInfoResponseDto member1, MemberInfoResponseDto member2, List<ChatResponseDto> chats ){
        return new ChatRoomResponse(
                chatRoom.getId(),
                member1,
                member2,
                chats,
                chatRoom.getCreatedAt()
        );
    }
}
