package com.sparta.soomtut.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatRequestDto {

    private Long senderId;
    private Long roomId;
    private String message;

}
