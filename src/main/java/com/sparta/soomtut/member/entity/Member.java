package com.sparta.soomtut.member.entity;

import com.sparta.soomtut.member.entity.enums.MemberRole;
import com.sparta.soomtut.member.entity.enums.MemberState;

import com.sparta.soomtut.auth.dto.request.RegisterRequest;

import com.sparta.soomtut.location.entity.Location;
import com.sparta.soomtut.util.constants.Constants;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
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
    @Column 
    private String provider;
    @Column
    private String oauthEmail;
    @Column
    @Enumerated(EnumType.STRING)
    private MemberState state;

    @OneToOne
    @Column
    private Location location;

    @Builder
    public Member(RegisterRequest request, Location location) {
        this.email = request.getEmail();
        this.nickname = request.getNickname();
     
        this.location = location;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateNickName(String nickname) {
        this.nickname = nickname;
    }

    public void updateProfileImage(String filePath) {
        this.image = filePath;
    }

    public void changeState(MemberState state) {
        this.state = state;
    }

    public boolean isActive() {
        return MemberState.ACTIVE.equals(state);
    }
}
