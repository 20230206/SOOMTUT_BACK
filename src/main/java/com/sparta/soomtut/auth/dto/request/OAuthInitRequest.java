package com.sparta.soomtut.auth.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OAuthInitRequest {
    private String nickname;
    
    private float posX;
    private float posY;
    private String address;
    private String sido;
    private String sigungu;
    private String bname;
}
