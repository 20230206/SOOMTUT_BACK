package com.sparta.soomtut.chat.config;

import org.springframework.web.socket.handler.TextWebSocketHandler;

public class WebSocketHandler extends TextWebSocketHandler {
    // 나중에 확장용으로 적어둠

//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//
//        /* afterConnectionClosed 메서드는 클라이언트 또는 서버에 의해 WebSocket 연결이 닫힐 때 호출됩니다.
//        이 방법을 사용하여 항목에 대한 클라이언트의 헤드라인 등록을 제거하거나
//        클라이언트의 연결이 끊겼다는 사실을 반영하도록 데이터베이스를 업데이트하는 등 필요한 정리 또는 리소스 릴리스 작업을 수행할 수 있습니다.*/
//
//    }
//
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        /* afterConnectionEstablished 메서드는 클라이언트와 서버 간에 WebSocket 연결이 설정된 후에 호출됩니다.
//        이 방법을 사용하여 클라이언트의 인증 정보를 확인하거나 항목에 대한 구독을 설정하는 등 필요한 초기화 또는 설정 작업을 수행할 수 있습니다.*/
//    }
}
