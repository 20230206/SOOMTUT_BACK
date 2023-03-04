package com.sparta.soomtut.chat.controller;

import com.sparta.soomtut.chat.dto.ChatResponseDto;
import com.sparta.soomtut.chat.service.ChatService;
import com.sparta.soomtut.util.response.SuccessCode;
import com.sparta.soomtut.util.response.ToResponse;
import com.sparta.soomtut.util.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatMessageController {

    private final ChatService chatService;

    @GetMapping("/message")
    public ResponseEntity<?> getLastChatMessage(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam Long roomId
    ) {
        ChatResponseDto data = chatService.getLastChatMessage(roomId);
        return ResponseEntity.ok(data);
    }
    // 채팅 메시지 전부 불러오기 (완료)
    @GetMapping("/messages")
    public ResponseEntity<?> getAllChatMessages(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam Long roomId
    ) {
        var data = chatService.getAllChatMessages(roomId);
        return ToResponse.of(data, SuccessCode.CHAT_GETMESSAGES_OK);
    }
}
