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

    @Column(name ="member1_id", nullable = false)
    private Long member1Id;

    @Column(name ="member2_id", nullable = false)
    private Long member2Id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    private ChatRoom(Long member1Id, Long member2Id) {
        this.member1Id = member1Id;
        this.member2Id = member2Id;
        this.createdAt = LocalDateTime.now();
    }

    public static ChatRoom of( Long member1Id, Long member2Id){
        return new ChatRoom(member1Id, member2Id);
    }


}
