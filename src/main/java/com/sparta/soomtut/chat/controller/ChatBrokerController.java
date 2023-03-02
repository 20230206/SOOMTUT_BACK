package com.sparta.soomtut.chat.controller;

import com.sparta.soomtut.chat.dto.ChatRequestDto;
import com.sparta.soomtut.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatBrokerController {

    private final ChatService chatService;
    private final SimpMessagingTemplate simpMessagingTemplate;


    // /message 인 클라이언트에서 들어오는 메시지를 처리
    @MessageMapping("/message") // 웹소켓 메시지 처리
    public void sendMessage(ChatRequestDto chatRequest) {
        // db 저장 메시지 하나 들어올 때, 저장
        chatService.save(chatRequest);

        ///subscribe/room/{roomId} 대상에 구독한 모든 클라이언트에게 메시지를 전송
        simpMessagingTemplate.convertAndSend("/subscribe/room/" + chatRequest.getRoomId(), chatRequest);
    }

}

// 프론트에서는??

/* 1. stompClient.subscribe() 메소드를 사용하여 /subscribe/room/{roomId} 대상을 구독한다.
   -> 서버가 메시지를 수신할 수 있도록 함.
   stompClient.subscribe('/subscribe/room/' + roomId, function(message)
* 2. stompClient.send() 메소드를 사용하여 서버에 메시지를 보낸다.
    -> var message = $('#message').val();
    stompClient.send('/publish/message', {}, JSON.stringify({roomId: roomId, message: message}));
    이 메시지는 백엔드의 sendMessage() 에서 처리된다.
 ++ */


/*만약 A와 B가 서로 채팅을 하다가 A가 웹소켓 연결을 끊는다면 어떻게 될까?
* 당연히 A가 채팅 메시지를 보내지 않으니 B는 어떤 메시지도 받지 못하다.
* 그러나 B는 메시지를 보낼 수 있으며 메시지를 보내는 순간 해당 메시지는 데이터 베이스에 저장된다.
* 우리는 roomId(채팅방번호)로 연결을 하기 때문에 채팅방번호를 구독한다면 언제든지 연결될 수 있다.
* 그리고 연결하는 순간(클라이언트가 채팅방을 들어오는 순간) db 에서 기존 대화 내용을 불로오면 된다.
* 둘이 같이 연결된 상태라면 메시지는 같은 채팅방을 구독하고 있는 상대에게 전달된다.*/





