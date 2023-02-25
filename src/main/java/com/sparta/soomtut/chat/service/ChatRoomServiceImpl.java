package com.sparta.soomtut.chat.service;

import com.sparta.soomtut.chat.dto.ChatRoomResponse;
import com.sparta.soomtut.chat.entity.ChatRoom;
import com.sparta.soomtut.chat.repository.ChatRoomRepository;
import com.sparta.soomtut.member.entity.Member;
import com.sparta.soomtut.member.service.MemberService;
import com.sparta.soomtut.service.interfaces.PostService;
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
    private final PostService postService;

    // 새로운 채팅방 생성
    @Override
    public ChatRoom createRoom(Long tuteeId, Long postId) {
        Member tutor = postService.getPostById(postId).getMember();
        // if(chatRoomRepository.existsByTuteeIdAndTutorId(tuteeId, tutor.getId())){
        //     throw new IllegalArgumentException(ErrorCode.DUPLICATED_CHATTING.getMessage());
        // }
        ChatRoom chatRoom = ChatRoom.of(tuteeId,tutor.getId(), postId);
        return chatRoomRepository.save(chatRoom);
    }

    // 채팅방 하나만 가져오기
    @Override
    public ChatRoomResponse getMyChatRoom(Long tuteeId, Long postId) {
        ChatRoom chatRoom = chatRoomRepository.findByTuteeIdOrPostId(tuteeId, postId)
                .orElseGet(()-> createRoom(tuteeId, postId));


        return ChatRoomResponse.of(chatRoom,
                memberService.getMemberInfoResponseDto(chatRoom.getTuteeId()),
                memberService.getMemberInfoResponseDto(chatRoom.getTutorId()),
                chatService.getAllChatMessages(chatRoom.getId()),
                postService.getPost(chatRoom.getPostId()));
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
                postService.getPost(chatRoom.getPostId())));
    }


    // 지원 함수
    @Override
    public Page<ChatRoom> getAllMyChatRooms(Long memberId, Pageable pageable) {
        return chatRoomRepository.findAllByTuteeIdOrTutorId(memberId, memberId, pageable);
    }


}
