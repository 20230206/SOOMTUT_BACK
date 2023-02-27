package com.sparta.soomtut.chat.repository;

import com.sparta.soomtut.chat.entity.ChatRoom;

import java.util.Optional;

import com.sparta.soomtut.lectureRequest.entity.LectureRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Page<ChatRoom> findAllByTuteeIdOrTutorId(Long tuteeId, Long tutorId, Pageable pageable);
    Optional<ChatRoom> findByLectureRequest(LectureRequest lectureRequest);

}









//    boolean existsByTuteeIdAndTutorId(Long tuteeId, Long tutorId);
// Optional<ChatRoom> findByTuteeIdOrLectureRequest(Long tuteeId, LectureRequest lectureRequest);