package com.sparta.soomtut.entity;

import com.sparta.soomtut.enums.MemberRole;
import com.sparta.soomtut.util.constants.Constants;

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
    private String nickname;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MemberRole memberRole;

    @Column(nullable = false)
    private LocalDate createdAt;
    
    @Column
    private float starRating;

    @Column
    private int level;

    @Column
    private String image;



    public Member(String email, String password, String nickname) {

        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.memberRole = MemberRole.MEMBER;
        this.createdAt = LocalDate.now();
        this.starRating = 0.0f;
        this.level = 0;
        this.image = Constants.STANDARD_USER_IMAGE;
    }

    public void updateNickName(String nickname) {
        this.nickname = nickname;
    }
}
