package com.sparta.soomtut.chat.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // 클라이언트에서 서버로 WebSocket 연결할 때 '/connect' 로 연결
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        registry.addEndpoint("/connect")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        registry.enableSimpleBroker("/subscribe");
        registry.setApplicationDestinationPrefixes("/publish");

    }

}


// 클라이언트는 엔드포인트를 /publish/대상 으로 지정해서 메시지 보냄
// 서버는 주로 @MessageMapping 으로 주석이 달린 메시지 핸들링 메서드로 메시지를 전달합니다.
// 메시지가 다른 WebSocket 클라이언트를 위한 것이면, 서버는 /subscribe 엔드포인트를 사용하여 해당 클라이언트에게 메시지를 전달합니다.
