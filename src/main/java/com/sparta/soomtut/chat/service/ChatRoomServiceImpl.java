package com.sparta.soomtut.chat.service;

import com.sparta.soomtut.chat.dto.ChatRoomResponse;
import com.sparta.soomtut.chat.entity.ChatRoom;
import com.sparta.soomtut.chat.repository.ChatRoomRepository;
import com.sparta.soomtut.entity.Member;
import com.sparta.soomtut.service.interfaces.MemberService;
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

    // 새로운 채팅방 생성
    @Override
    public void createRoom(Long memberId, String recipientNickname) {
        Member member = memberService.getMemberByNickname(recipientNickname);
        if(chatRoomRepository.existsBySenderIdAndRecipientId(memberId, member.getId())){
            throw new IllegalArgumentException(ErrorCode.DUPLICATED_CHATTING.getMessage());
        }
        ChatRoom chatRoom = ChatRoom.of(memberId,member.getId());
        chatRoomRepository.save(chatRoom);
    }

    // 채팅방 하나만 가져오기
    @Override
    public ChatRoomResponse getMyChatRoom(Long roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(()->new IllegalArgumentException(ErrorCode.NOT_FOUND_CHATROOM.getMessage()));
        return ChatRoomResponse.of(chatRoom,
                memberService.getMemberInfoResponseDto(chatRoom.getMember1Id()),
                memberService.getMemberInfoResponseDto(chatRoom.getMember2Id()),
                chatService.getAllChatMessages(chatRoom.getId()));
    }

    // 채팅방 여러개 가져오기
    @Override
    @Transactional(readOnly = true)
    public Page<ChatRoomResponse> getMyChatRooms(Long memberId, Pageable pageable) {
        Page<ChatRoom> chatRoomList = getAllMyChatRooms(memberId,pageable);

        return chatRoomList.map(chatRoom -> ChatRoomResponse.of(chatRoom,
                memberService.getMemberInfoResponseDto(chatRoom.getMember1Id()),
                memberService.getMemberInfoResponseDto(chatRoom.getMember2Id()),
                chatService.getAllChatMessages(chatRoom.getId())));
    }


    // 지원 함수
    @Override
    public Page<ChatRoom> getAllMyChatRooms(Long memberId, Pageable pageable) {
        return chatRoomRepository.findAllBySenderIdAndRecipientId(memberId, memberId, pageable);
    }


}
