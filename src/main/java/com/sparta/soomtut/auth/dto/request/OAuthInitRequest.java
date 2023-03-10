package com.sparta.soomtut.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OAuthInitRequest {
    private String nickname;
    
    private float posX;
    private float posY;
    private String address;
    private String sido;
    private String sigungu;
    private String bname;
}
