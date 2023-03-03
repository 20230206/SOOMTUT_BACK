package com.sparta.soomtut.lecture.entity;

import com.sparta.soomtut.member.entity.Member;
import com.sparta.soomtut.util.entity.TimeStamped;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Bookmark extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id", nullable = false)
    private Lecture post;

    // 북마크 true = 추가, false = 취소
    @Column(nullable = false)
    private boolean status;

    @Builder
    public Bookmark(Lecture post, Member member){
        this.post = post;
        this.member = member;
        this.status = true;
    }

    public void updateState(boolean value) {
        this.status = value;
    }

}
