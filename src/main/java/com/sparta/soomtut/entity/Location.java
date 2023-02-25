package com.sparta.soomtut.entity;

import com.sparta.soomtut.dto.request.LocationRequestDto;
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
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="member_id")
    private Member member;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private float vectorX;

    @Column(nullable = false)
    private float vectorY;

    @Builder(builderClassName = "ForNewMember", builderMethodName="forNewMember")
    public Location(Member member,String address, float vectorX, float vectorY) {
        this.member = member;
        this.address = address;
        this.vectorX = vectorX;
        this.vectorY = vectorY;
    }

    //위치정보 테스트를 위한 생성자
    @Builder
    public Location(String address, float vectorX, float vectorY) {
        this.address = address;
        this.vectorX = vectorX;
        this.vectorY = vectorY;
    }

    public void updateLocation(LocationRequestDto request) {
        this.address = request.getAddress();
        this.vectorX = request.getVectorX();
        this.vectorY = request.getVectorY();
    }

    public void updateAddress(String address) {
        this.address = address;
    }
}
