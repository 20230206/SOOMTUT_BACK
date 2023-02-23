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

    // 채팅방 가져오기 (생성 or 조회)
    @PostMapping("/{postId}")
    public ResponseEntity<?> getMyChatRoom(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long postId) {
        ChatRoomResponse data = chatRoomService.getMyChatRoom(postId, userDetails.getMemberId());
        return ToResponse.of(data, SuccessCode.MESSGE_OK);
    }

    // 나의 채팅방 목록 조회 (완료)
    @GetMapping
    public ResponseEntity<?> getMyChatRooms(@AuthenticationPrincipal UserDetailsImpl userDetails, @ModelAttribute PageRequestDto pageable) {
        var data = chatRoomService.getMyChatRooms(userDetails.getMemberId(),pageable.toPageable());
        return ToResponse.of(data, SuccessCode.MESSGE_OK);
    }

}
