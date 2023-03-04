package com.sparta.soomtut.chat.repository;

import com.sparta.soomtut.chat.entity.ChatRoom;

import java.util.Optional;

import com.sparta.soomtut.lectureRequest.entity.LectureRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sparta.soomtut.lectureRequest.entity.LectureState;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Page<ChatRoom> findAllByTuteeIdOrTutorId(Long tuteeId, Long tutorId, Pageable pageable);

    @Query("SELECT cr FROM ChatRoom cr JOIN cr.lectureRequest lr WHERE (cr.tuteeId=:tuteeId OR cr.tutorId=:tutorId) AND lr.lectureState=:state")
    Page<ChatRoom> findAllByTuteeIdOrTutorIdAndLectureState(@Param("tuteeId")Long tuteeId, @Param("tutorId")Long tutorId, @Param("state")LectureState state, Pageable pageable);

    Optional<ChatRoom> findByLectureRequest(LectureRequest lectureRequest);
    @Query("SELECT c FROM ChatRoom c WHERE c.lectureRequest.id = :lectureRequestId")
    ChatRoom findByLectureRequestId(@Param("lectureRequestId") Long lectureRequestId);
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM ChatRoom c WHERE c.lectureRequest.id = :lectureRequestId")
    boolean existsByLectureRequestId(@Param("lectureRequestId") Long lectureRequestId);
    Optional<ChatRoom> findByTuteeIdAndLectureRequestId(Long TuteeId, Long LectureRequestId);
    Optional<ChatRoom> findByTutorIdAndLectureRequestId(Long tutorId, Long lectureRequestId);

}









//    boolean existsByTuteeIdAndTutorId(Long tuteeId, Long tutorId);
// Optional<ChatRoom> findByTuteeIdOrLectureRequest(Long tuteeId, LectureRequest lectureRequest);