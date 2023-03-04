package com.sparta.soomtut.chat.service.impl;

import com.sparta.soomtut.chat.dto.ChatRoomResponse;
import com.sparta.soomtut.chat.entity.ChatRoom;
import com.sparta.soomtut.chat.repository.ChatRoomRepository;
import com.sparta.soomtut.chat.service.ChatRoomService;
import com.sparta.soomtut.lecture.service.LectureService;
import com.sparta.soomtut.lectureRequest.service.LectureRequestService;
import com.sparta.soomtut.lectureRequest.entity.LectureState;
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

        if (chatRoomRepository.existsByLectureRequestId(lectureRequestId)) {
            chatRoom = getChatRoomByTuteeIdAndLectureRequestId(tuteeId, lectureRequestId);
        }
        else {
            chatRoom = createChatRoom(lectureRequestId);
        }
        
        return ChatRoomResponse.of(
                chatRoom,
                memberService.getMemberInfoResponseDto(chatRoom.getTuteeId()),
                memberService.getMemberInfoResponseDto(chatRoom.getTutorId()),
                lectureService.getLecture(chatRoom.getLectureId()),
                chatRoom.getLectureRequest());
    }
    
    @Transactional
    public ChatRoom createChatRoom(Long lectureRequestId) {
        var lectureRequest = lectureRequestService.getLectureRequestById(lectureRequestId);
        ChatRoom chatRoom = ChatRoom.of(lectureRequest);
        return chatRoomRepository.save(chatRoom);
    }

    @Transactional(readOnly = true)
    public ChatRoom getChatRoomByTuteeIdAndLectureRequestId(Long tuteeId, Long lectureRequestId) {
        return chatRoomRepository.findByTuteeIdAndLectureRequestId(tuteeId, lectureRequestId).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND_CHATROOM)
        );
    }

    @Override
    public ChatRoomResponse getChatRoomForTutor(Long tutorId, Long lectureRequestId) {
        ChatRoom chatRoom = getChatRoomByTutorIdAndLectureRequestId(tutorId, lectureRequestId);
        return ChatRoomResponse.of(
                chatRoom,
                memberService.getMemberInfoResponseDto(chatRoom.getTuteeId()),
                memberService.getMemberInfoResponseDto(chatRoom.getTutorId()),
                lectureService.getLecture(chatRoom.getLectureId()),
                chatRoom.getLectureRequest());
    }

    @Transactional(readOnly=true)
    public ChatRoom getChatRoomByTutorIdAndLectureRequestId(Long tutorId, Long lectureRequestId) {
        return chatRoomRepository.findByTutorIdAndLectureRequestId(tutorId, lectureRequestId)
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CHATROOM));
    }

    // 채팅방 여러개 가져오기
    @Override
    @Transactional(readOnly = true)
    public Page<ChatRoomResponse> getMyChatRooms(Long memberId, int state, Pageable pageable) {

        if(state == 0) {
            Page<ChatRoom> chatRooms = getAllMyChatRooms(memberId, pageable);

            return chatRooms.map(chatRoom -> ChatRoomResponse.of(chatRoom,
                    memberService.getMemberInfoResponseDto(chatRoom.getTuteeId()),
                    memberService.getMemberInfoResponseDto(chatRoom.getTutorId()),
                    lectureService.getLecture(chatRoom.getLectureId()),
                    chatRoom.getLectureRequest()));
        }
        else {
            Page<ChatRoom> chatRooms = getAllMyChatRoomsByState(memberId, state, pageable);
            
            return chatRooms.map(chatRoom -> ChatRoomResponse.of(chatRoom,
                    memberService.getMemberInfoResponseDto(chatRoom.getTuteeId()),
                    memberService.getMemberInfoResponseDto(chatRoom.getTutorId()),
                    lectureService.getLecture(chatRoom.getLectureId()),
                    chatRoom.getLectureRequest()));
        }
    }

    @Override
    public Page<ChatRoom> getAllMyChatRooms(Long memberId, Pageable pageable) {
        return chatRoomRepository.findAllByTuteeIdOrTutorId(memberId, memberId, pageable);
    }
    
    private Page<ChatRoom> getAllMyChatRoomsByState(Long memberId, int state, Pageable pageable) {
        return chatRoomRepository.findAllByTuteeIdOrTutorIdAndLectureState(memberId, memberId, LectureState.valueOf(state), pageable);
    }

}
