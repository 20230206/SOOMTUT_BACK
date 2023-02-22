package com.sparta.soomtut.chat.controller;

import com.sparta.soomtut.chat.dto.ChatResponseDto;
import com.sparta.soomtut.chat.repository.ChatMessageRepository;
import com.sparta.soomtut.chat.service.ChatService;
import com.sparta.soomtut.util.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatMessageController {


    private final ChatMessageRepository chatMessageRepository;
    private final ChatService chatService;

    // 채팅 메시지 하나 불러오기
    @GetMapping
    public ResponseEntity<?> getLastChatMessage(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam Long roomId) {
        ChatResponseDto data = chatService.getLastChatMessage(roomId);
        return ResponseEntity.ok(data);

    }
    // 채팅 메시지 전부 불러오기 (완료 )
    @GetMapping
    public ResponseEntity<List<ChatResponseDto>> getAllChatMessages(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam Long roomId) {
        List<ChatResponseDto> data = chatService.getAllChatMessages(roomId);
        return ResponseEntity.ok().body(data);

    }



}
