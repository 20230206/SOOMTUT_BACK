package com.sparta.soomtut.chat.controller;

import com.sparta.soomtut.chat.dto.ChatRequestDto;
import com.sparta.soomtut.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatBrokerController {

    private final ChatService chatService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    // /message 인 클라이언트에서 들어오는 메시지를 처리
    @MessageMapping("/message") // 웹소켓 메시지 처리
    public void sendMessage(ChatRequestDto chatRequest) {
        // db 저장 메시지 하나 들어올 때, 저장
        chatService.save(chatRequest);

        ///subscribe/room/{roomId} 대상에 구독한 모든 클라이언트에게 메시지를 전송
        simpMessagingTemplate.convertAndSend(
                "/subscribe/room/" + chatRequest.getRoomId(),
                chatRequest
        );
    }

}