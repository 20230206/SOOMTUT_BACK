package com.sparta.soomtut.chat.service.impl;

import com.sparta.soomtut.chat.dto.ChatRoomResponse;
import com.sparta.soomtut.chat.entity.ChatRoom;
import com.sparta.soomtut.chat.repository.ChatRoomRepository;
import com.sparta.soomtut.chat.service.ChatRoomService;
import com.sparta.soomtut.lecture.service.LectureService;
import com.sparta.soomtut.lectureRequest.service.LectureRequestService;
import com.sparta.soomtut.member.service.MemberService;
import com.sparta.soomtut.util.exception.CustomException;
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
    private final LectureService lectureService;
    private final LectureRequestService lectureRequestService;


    @Override
    public ChatRoomResponse getChatRoomForTutee(Long tuteeId, Long lectureRequestId) {
        ChatRoom chatRoom;
        if(chatRoomRepository.existsByLectureRequestId(lectureRequestId)) {
            chatRoom = getChatRoomByTuteeIdAndLectureRequestId(tuteeId, lectureRequestId);
        }
        else {
            chatRoom = createChatRoom(tuteeId, lectureRequestId);
        }
        
        return ChatRoomResponse.of(chatRoom, 
                                   memberService.getMemberInfoResponseDto(chatRoom.getTuteeId()), 
                                   memberService.getMemberInfoResponseDto(chatRoom.getTutorId()),
                                   lectureService.getLecture(chatRoom.getLectureId()),
                                   chatRoom.getLectureRequest().getId());
    }
    
    @Transactional
    private ChatRoom createChatRoom(Long tuteeId, Long lectureRequestId) {
        var lectureRequest = lectureRequestService.getLectureRequestById(lectureRequestId);
        ChatRoom chatRoom = ChatRoom.of(lectureRequest);
        return chatRoomRepository.save(chatRoom);
        
    }

    @Transactional(readOnly = true)
    private ChatRoom getChatRoomByTuteeIdAndLectureRequestId(Long tuteeId, Long lectureRequestId) {
        return chatRoomRepository.findByTuteeIdAndLectureRequestId(tuteeId, lectureRequestId).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND_CHATROOM)
        );
    }

    @Override
    public ChatRoomResponse getChatRoomForTutor(Long tutorId, Long lectureRequestId) {
        ChatRoom chatRoom = getChatRoomByTutorIdAndLectureRequestId(tutorId, lectureRequestId);
        return ChatRoomResponse.of(chatRoom, 
                                    memberService.getMemberInfoResponseDto(chatRoom.getTuteeId()), 
                                    memberService.getMemberInfoResponseDto(chatRoom.getTutorId()),
                                    lectureService.getLecture(chatRoom.getLectureId()),
                                    chatRoom.getLectureRequest().getId());
    }

    @Transactional(readOnly=true)
    private ChatRoom getChatRoomByTutorIdAndLectureRequestId(Long tutorId, Long lectureRequestId) {
        return chatRoomRepository.findByTutorIdAndLectureRequestId(tutorId, lectureRequestId)
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CHATROOM));
    }


    // 채팅방 여러개 가져오기
    @Override
    @Transactional(readOnly = true)
    public Page<ChatRoomResponse> getMyChatRooms(Long memberId, Pageable pageable) {
        Page<ChatRoom> chatRoomList = getAllMyChatRooms(memberId,pageable);

        return chatRoomList.map(chatRoom -> ChatRoomResponse.of(chatRoom,
                memberService.getMemberInfoResponseDto(chatRoom.getTuteeId()),
                memberService.getMemberInfoResponseDto(chatRoom.getTutorId()),
                lectureService.getLecture(chatRoom.getLectureId()),
                chatRoom.getLectureRequest().getId()));
    }

    // 지원 함수
    @Override
    public Page<ChatRoom> getAllMyChatRooms(Long memberId, Pageable pageable) {
        return chatRoomRepository.findAllByTuteeIdOrTutorId(memberId, memberId, pageable);
    }


}
