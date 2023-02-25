package com.sparta.soomtut.chat.dto;

import com.sparta.soomtut.chat.entity.ChatRoom;
import com.sparta.soomtut.lecture.dto.response.PostResponseDto;
import com.sparta.soomtut.member.dto.response.MemberInfoResponse;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ChatRoomResponse {

    private Long id;
    private MemberInfoResponse tutee;

    private MemberInfoResponse tutor;

    private List<ChatResponseDto> chats;

    private PostResponseDto post;

    private LocalDateTime createdAt;
    private ChatRoomResponse(
            Long id,
            MemberInfoResponse tutee,
            MemberInfoResponse tutor,
            List<ChatResponseDto> chats,
            LocalDateTime createdAt,
            PostResponseDto post ) {

        this.id = id;
        this.tutee = tutee;
        this.tutor = tutor;
        this.chats = chats;
        this.createdAt = createdAt;
        this.post = post;
    }

    public static ChatRoomResponse of(
            ChatRoom chatRoom,
            MemberInfoResponse tutee,
            MemberInfoResponse tutor,
            List<ChatResponseDto> chats,
            PostResponseDto post){
        return new ChatRoomResponse(
                chatRoom.getId(),
                tutee,
                tutor,
                chats,
                chatRoom.getCreatedAt(),
                post
        );
    }
}
