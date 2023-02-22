package com.sparta.soomtut.chat.controller;

import com.sparta.soomtut.chat.dto.ChatRoomResponse;
import com.sparta.soomtut.chat.service.ChatRoomService;
import com.sparta.soomtut.dto.request.PageRequestDto;
import com.sparta.soomtut.util.response.SuccessCode;
import com.sparta.soomtut.util.response.ToResponse;
import com.sparta.soomtut.util.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/chat_room")
public class ChatRoomController {


    private final ChatRoomService chatRoomService;

    // 채팅방 개설(완료) - 매개변수로 뭘 받을 지 모르겠음.
    @PostMapping
        public void createRoom(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam String recipientNickname) {
        chatRoomService.createRoom(userDetails.getMemberId(), recipientNickname);
    }

    // 채팅방 가져오기 (완료)
    @PostMapping
    public ResponseEntity<?> getMyChatRoom(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam Long roomId) {
        ChatRoomResponse data = chatRoomService.getMyChatRoom(roomId);
        return ToResponse.of(data, SuccessCode.MESSGE_OK);
    }

    // 나의 채팅방 목록 조회 (완료)
    @GetMapping
    public ResponseEntity<?> getMyChatRooms(@AuthenticationPrincipal UserDetailsImpl userDetails, @ModelAttribute PageRequestDto pageable) {
        var data = chatRoomService.getMyChatRooms(userDetails.getMemberId(),pageable.toPageable());
        return ToResponse.of(data, SuccessCode.MESSGE_OK);
    }

}
