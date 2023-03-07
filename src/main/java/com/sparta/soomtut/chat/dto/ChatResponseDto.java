package com.sparta.soomtut.chat.dto;

import com.sparta.soomtut.chat.entity.ChatMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ChatResponseDto {

    private Long id;
    private Long senderId;
    private String message;
    private LocalDateTime sentAt;

    private ChatResponseDto(ChatMessage chatMessage) {
        this.id = chatMessage.getId();
        this.senderId = chatMessage.getSenderId();
        this.message = chatMessage.getContent();
        this.sentAt = chatMessage.getSentAt();
    }

    public static ChatResponseDto of(ChatMessage chatMessage) {
        return new ChatResponseDto(
                chatMessage
        );
    }

}
