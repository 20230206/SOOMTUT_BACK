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

    // /subscribe/rooms/{roomId} 채널로 메시지를 전송
    @MessageMapping("/message")
    public void sendMessage(ChatRequestDto chatRequest) {
        chatService.save(chatRequest); // db 저장 메시지 하나 들어올 때, 저장
        simpMessagingTemplate.convertAndSend("/subscribe/room/" + chatRequest.getRoomId(), chatRequest.getMessage());
    }

}


// JS 에서 사용 방식
//// Create a SockJS instance
//const socket = new SockJS("http://localhost:8080/connect");
//
//// Create a STOMP client instance
//        const stompClient = Stomp.over(socket);
//
//// Event listener for when the connection is established
//        stompClient.connect({}, (frame) => {
//        console.log("WebSocket connection is open!");
//        });
//
//// Send a message to the server
//        const message = {
//        senderId: "user1",
//        roomId: "room1",
//        message: "Hello, server!"
//        };
//
//        stompClient.send("/publish/message", {}, JSON.stringify(message));


// 적절한 목적지에 대한 구독을 등록합니다.
//  stompClient.subscribe("/subscribe/room/room1", (message) => {
//          console.log(`수신한 메시지: ${message.body}`);
//          });

//    // Event listener for when the chat ends or the user logs out
//    function endChat() {
//        // Disconnect the SockJS connection
//        stompClient.disconnect(() => {
//                console.log("WebSocket connection is closed!");
//  });

//위에 코드를 React 로 변환한 코드

/*import React, { useEffect } from "react";
        import SockJS from "sockjs-client";
        import Stomp from "stompjs";

        function ChatComponent() {
        const sendMessage = () => {
        // Send a message to the server
        const message = {
        senderId: "user1",
        roomId: "room1",
        message: "Hello, server!"
        };

        stompClient.send("/publish/message", {}, JSON.stringify(message));
        }

        const endChat = () => {
        // Disconnect the SockJS connection
        stompClient.disconnect(() => {
        console.log("WebSocket connection is closed!");
        });
        }

        useEffect(() => {
        // Create a SockJS instance
        const socket = new SockJS("http://localhost:8080/connect");

        // Create a STOMP client instance
        const stompClient = Stomp.over(socket);

        // Event listener for when the connection is established
        stompClient.connect({}, (frame) => {
        console.log("WebSocket connection is open!");
        });

        return () => {
        // Disconnect the SockJS connection when the component unmounts
        stompClient.disconnect(() => {
        console.log("WebSocket connection is closed!");
        });
        }
        }, []);

        return (
<div>
<button onClick={sendMessage}>Send Message</button>
<button onClick={endChat}>End Chat</button>
</div>
        );
        }

        export default ChatComponent;*/

