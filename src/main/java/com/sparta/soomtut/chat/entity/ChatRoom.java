package com.sparta.soomtut.chat.entity;

import com.sparta.soomtut.lecture.entity.Lecture;
import com.sparta.soomtut.lectureRequest.entity.LectureRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="tutor_id", nullable = false)
    private Long tutorId;

    @Column(name ="tutee_id", nullable = false)
    private Long tuteeId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "lecture_request_id", referencedColumnName = "id")
    private LectureRequest lectureRequest;

    private ChatRoom(LectureRequest lectureRequest) {
        this.tutorId = lectureRequest.getTutorId();
        this.tuteeId = lectureRequest.getTuteeId();
        this.lectureRequest = lectureRequest;
        this.createdAt = LocalDateTime.now();
    }

    public static ChatRoom of(LectureRequest lectureRequest){
        return new ChatRoom(lectureRequest);
    }

    public Long getTuteeId(){
        return lectureRequest.getTuteeId();
    }

    public Long getLectureId(){
        return lectureRequest.getLectureId();
    }

}







//    @Column(name ="post_id", nullable = false)
//    private Long postId;
//    @Column(name ="tutee_id", nullable = false)
//    private Long tuteeId;
