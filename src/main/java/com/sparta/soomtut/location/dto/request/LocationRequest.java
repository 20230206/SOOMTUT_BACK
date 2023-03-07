package com.sparta.soomtut.location.dto.request;

import com.sparta.soomtut.auth.dto.request.RegisterRequest;
import com.sparta.soomtut.auth.dto.request.OAuthInitRequest;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LocationRequest {
    private float posX;
    private float posY;
    private String address;
    private String sido;
    private String sigungu;
    private String bname;

    @Builder(builderClassName="RegisterRequestConvert", builderMethodName="registerConvert")
    public LocationRequest (RegisterRequest request) {
        this.posX = request.getPosX();
        this.posY = request.getPosY();
        this.address = request.getAddress();
        this.sido = request.getSido();
        this.sigungu = request.getSigungu();
        this.bname = request.getBname();
    }

    @Builder(builderClassName="OAuthInitRequestConvert", builderMethodName="oauthInitconvert")
    public LocationRequest (OAuthInitRequest request) {
        this.posX = request.getPosX();
        this.posY = request.getPosY();
        this.address = request.getAddress();
        this.sido = request.getSido();
        this.sigungu = request.getSigungu();
        this.bname = request.getBname();
    }

}
