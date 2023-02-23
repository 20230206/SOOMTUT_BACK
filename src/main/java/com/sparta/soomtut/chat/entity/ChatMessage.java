package com.sparta.soomtut.chat.entity;

import com.sparta.soomtut.chat.dto.ChatRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    private Long senderId;

    private LocalDateTime sentAt;

    private Long roomId;

    private ChatMessage(String content, Long senderId, Long roomId) {
        this.content = content;
        this.senderId = senderId;
        this.sentAt = LocalDateTime.now();
        this.roomId = roomId;
    }

    public static ChatMessage of(ChatRequestDto chatRequestDto){
        return new ChatMessage(
                chatRequestDto.getMessage(),
                chatRequestDto.getSenderId(),
                chatRequestDto.getRoomId()
        );
    }


}
