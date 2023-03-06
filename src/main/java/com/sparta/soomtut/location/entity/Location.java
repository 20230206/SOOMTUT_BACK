package com.sparta.soomtut.location.entity;

import com.sparta.soomtut.location.dto.request.LocationUpdateRequest;
import com.sparta.soomtut.member.entity.Member;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private float posX;

    @Column(nullable = false)
    private float posY;

    @Column
    private String sido;
    
    @Column
    private String sigungu;
    
    @Column
    private String bname;



}
