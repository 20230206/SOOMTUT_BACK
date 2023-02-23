package com.sparta.soomtut.chat.entity;

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
    @Column(name ="room_id")
    private Long id;

    @Column(name ="tutee_id", nullable = false)
    private Long tuteeId;

    @Column(name ="tutor_id", nullable = false)
    private Long tutorId;

    @Column(name ="post_id", nullable = false)
    private Long postId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    private ChatRoom(Long tuteeId, Long tutorId, Long postId) {
        this.tuteeId = tuteeId;
        this.tutorId = tutorId;
        this.postId = postId;
        this.createdAt = LocalDateTime.now();
    }

    public static ChatRoom of( Long tuteeId, Long tutorId, Long postId){
        return new ChatRoom(tuteeId, tutorId, postId);
    }


}
