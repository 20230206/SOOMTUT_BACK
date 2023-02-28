package com.sparta.soomtut.chat.service;

import com.sparta.soomtut.chat.dto.ChatRoomResponse;
import com.sparta.soomtut.chat.entity.ChatRoom;
import com.sparta.soomtut.chat.repository.ChatRoomRepository;
import com.sparta.soomtut.lecture.entity.Lecture;
import com.sparta.soomtut.lecture.service.LectureService;
import com.sparta.soomtut.lectureRequest.entity.LectureRequest;
import com.sparta.soomtut.lectureRequest.service.LectureRequestService;
import com.sparta.soomtut.member.entity.Member;
import com.sparta.soomtut.member.service.MemberService;

import com.sparta.soomtut.util.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService{

    private final ChatRoomRepository chatRoomRepository;
    private final MemberService memberService;
    private final ChatService chatService;
    private final LectureService lectureService;
    private final LectureRequestService lectureRequestService;

    // 새로운 채팅방 생성
    @Override
    public ChatRoomResponse createRoom(Long memberId, Long lectureRequestId) {
        // 기존 채팅방이 있는지 없는지 확인.
        if(!chatRoomRepository.existsByLectureRequestId(lectureRequestId)){
            new IllegalArgumentException(ErrorCode.NOT_FOUND_CHATROOM.getMessage());
        }
        LectureRequest lectureRequest = lectureRequestService.getLectureRequestById(lectureRequestId);
        ChatRoom chatRoom = ChatRoom.of(lectureRequest);
        chatRoomRepository.save(chatRoom);

        return ChatRoomResponse.of(chatRoom
                , memberService.getMemberInfoResponseDto(chatRoom.getTuteeId())
                , memberService.getMemberInfoResponseDto(chatRoom.getTutorId())
                ,chatService.getAllChatMessages(chatRoom.getId())
                ,lectureService.getLecture(chatRoom.getLectureId())
        );
    }

    // 채팅방 여러개 가져오기
    @Override
    @Transactional(readOnly = true)
    public Page<ChatRoomResponse> getMyChatRooms(Long memberId, Pageable pageable) {
        Page<ChatRoom> chatRoomList = getAllMyChatRooms(memberId,pageable);

        return chatRoomList.map(chatRoom -> ChatRoomResponse.of(chatRoom,
                memberService.getMemberInfoResponseDto(chatRoom.getTuteeId()),
                memberService.getMemberInfoResponseDto(chatRoom.getTutorId()),
                chatService.getAllChatMessages(chatRoom.getId()),
                lectureService.getLecture(chatRoom.getLectureId())));
    }

    // 지원 함수
    @Override
    public Page<ChatRoom> getAllMyChatRooms(Long memberId, Pageable pageable) {
        return chatRoomRepository.findAllByTuteeIdOrTutorId(memberId, memberId, pageable);
    }


}
