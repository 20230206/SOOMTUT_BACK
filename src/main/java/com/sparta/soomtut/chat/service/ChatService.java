package com.sparta.soomtut.chat.service;

import com.sparta.soomtut.chat.dto.ChatRequestDto;
import com.sparta.soomtut.chat.dto.ChatResponseDto;

import java.util.List;

public interface ChatService {

    void save(ChatRequestDto chatRequest);
    ChatResponseDto getLastChatMessage(Long memberId);
    List<ChatResponseDto> getAllChatMessages(Long memberId);

}
