package com.sparta.soomtut.chat.controller;

import com.sparta.soomtut.chat.service.ChatRoomService;
import com.sparta.soomtut.util.dto.request.PageRequestDto;
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

    @PostMapping("/{lectureRequestId}/tutee")
    public ResponseEntity<?> getChatRoomForTutee(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long lectureRequestId)
    {
        var date = chatRoomService.getChatRoomForTutee(userDetails.getMemberId(), lectureRequestId);
        // 성공 메시지 바꿔야함.
        return ToResponse.of(date, SuccessCode.CHATROOM_GETTUTEE_OK);
    }
    
    @PostMapping("/{lectureRequestId}/tutor")
    public ResponseEntity<?> getChatRoomForTutor(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long lectureRequestId)
    {
        var date = chatRoomService.getChatRoomForTutor(userDetails.getMemberId(), lectureRequestId);
        // 성공 메시지 바꿔야함.
        return ToResponse.of(date, SuccessCode.CHATROOM_GETTUTOR_OK);
    }

    // 나의 채팅방 목록 조회 (완료)
    @GetMapping
    public ResponseEntity<?> getMyChatRooms(
        @RequestParam("state") int state,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @ModelAttribute PageRequestDto pageable) {
        var data = chatRoomService.getMyChatRooms(userDetails.getMemberId(), state, pageable.toPageable());
        // 성공 메시지 바꿔야함.
        return ToResponse.of(data, SuccessCode.CHATROOM_GETMYCHATLIST_OK);
    }

}



