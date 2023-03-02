package com.sparta.soomtut.chat.dto;

import com.sparta.soomtut.chat.entity.ChatRoom;
import com.sparta.soomtut.lecture.dto.response.LectureResponseDto;
import com.sparta.soomtut.member.dto.response.MemberInfoResponse;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ChatRoomResponse {

    private Long id;
    private MemberInfoResponse tutee;

    private MemberInfoResponse tutor;

    private LectureResponseDto lecture;

    private Long lecreqId;

    private LocalDateTime createdAt;
    private ChatRoomResponse(
            Long id,
            MemberInfoResponse tutee,
            MemberInfoResponse tutor,
            LocalDateTime createdAt,
            Long lecreqId,
            LectureResponseDto lecture ) {

        this.id = id;
        this.tutee = tutee;
        this.tutor = tutor;
        this.createdAt = createdAt;
        this.lecreqId = lecreqId;
        this.lecture = lecture;
    }

    public static ChatRoomResponse of(
            ChatRoom chatRoom,
            MemberInfoResponse tutee,
            MemberInfoResponse tutor,
            LectureResponseDto lecture,
            Long lecreqId){
        return new ChatRoomResponse(
                chatRoom.getId(),
                tutee,
                tutor,
                chatRoom.getCreatedAt(),
                lecreqId,
                lecture
        );
    }
}
