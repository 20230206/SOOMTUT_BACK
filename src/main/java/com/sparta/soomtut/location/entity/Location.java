package com.sparta.soomtut.location.entity;

import com.sparta.soomtut.location.dto.request.LocationRequest;

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

    @Column
    private String address;

    @Column
    private float posX;

    @Column
    private float posY;

    @Column
    private String sido;
    
    @Column
    private String sigungu;
    
    @Column
    private String bname;

    @Builder
    public Location(LocationRequest request) {
        this.address = request.getAddress();
        this.posX = request.getPosX();
        this.posY = request.getPosY();
        this.sido = request.getSido();
        this.sigungu = request.getSigungu();
        this.bname = request.getBname();
    }

    public void updateLocation(LocationRequest request) {
        this.address = request.getAddress();
        this.posX = request.getPosX();
        this.posY = request.getPosY();
        this.sido = request.getSido();
        this.sigungu = request.getSigungu();
        this.bname = request.getBname();

    }

}
