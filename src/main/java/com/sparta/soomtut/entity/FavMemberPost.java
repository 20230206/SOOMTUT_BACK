package com.sparta.soomtut.entity;

import com.sparta.soomtut.lecture.entity.Lecture;
import com.sparta.soomtut.member.entity.Member;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// lombok
@Getter
@NoArgsConstructor
// jpa
@Entity
public class FavMemberPost extends TimeStamped {
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
    public FavMemberPost(Lecture post, Member member){
        this.post = post;
        this.member = member;
        this.status = true;
    }

    public void updateState(boolean value) {
        this.status = value;
    }

}
