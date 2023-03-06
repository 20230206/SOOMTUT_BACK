package com.sparta.soomtut.chat.dto;

import com.sparta.soomtut.chat.entity.ChatRoom;
import com.sparta.soomtut.lecture.dto.response.LectureResponseDto;
import com.sparta.soomtut.lectureRequest.dto.LecReqResponseDto;
import com.sparta.soomtut.lectureRequest.entity.LectureRequest;
import com.sparta.soomtut.member.dto.response.MemberResponse;
import com.sparta.soomtut.member.entity.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ChatRoomResponse {

    private Long id;
    private LocalDateTime createdAt;

    private MemberResponse tutee;
    private MemberResponse tutor;
    private LectureResponseDto lecture;
    private LecReqResponseDto lectureRequest;

    @Builder(builderClassName="ChatRoomResponseToDto", builderMethodName="toDto")
    public ChatRoomResponse (ChatRoom chatRoom, Member tutee, LectureRequest lectureRequest)
    {
        this.id = chatRoom.getId();
        this.createdAt = chatRoom.getCreatedAt();

        this.tutee = MemberResponse.toDto().member(tutee).build();
        this.tutor = MemberResponse.toDto().member(lectureRequest.getLecture().getMember()).build();

        this.lecture = LectureResponseDto.toDto().lecture(lectureRequest.getLecture()).build();
        this.lectureRequest = LecReqResponseDto.toDto().lectureRequest(lectureRequest).build();
    }
}
