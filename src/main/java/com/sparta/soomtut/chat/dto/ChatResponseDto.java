package com.sparta.soomtut.chat.dto;

import com.sparta.soomtut.chat.entity.ChatMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ChatResponseDto {

    private Long senderId;
    private String message;
    private LocalDateTime sentAt;

    private ChatResponseDto(Long senderId, String message, LocalDateTime sentAt) {
        this.senderId = senderId;
        this.message = message;
        this.sentAt = sentAt;
    }

    public static ChatResponseDto of(ChatMessage chatMessage){
        return new ChatResponseDto(
                chatMessage.getSenderId(),
                chatMessage.getContent(),
                chatMessage.getSentAt()
        );
    }

}
