package com.sparta.soomtut.entity;

import com.sparta.soomtut.enums.MemberRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// lombok
@Getter
@NoArgsConstructor

// jpa
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MemberRole memberRole;

    @Column(nullable = false)
    private LocalDate createdAt;

    public Member(String email, String password,String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.memberRole = MemberRole.MEMBER;
        this.createdAt = LocalDate.now();
    }

    public void updateNickName(String nickname) {
        this.nickname = nickname;
    }
}
