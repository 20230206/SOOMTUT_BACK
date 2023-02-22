package com.sparta.soomtut.dto.response;

import com.sparta.soomtut.entity.Member;
import com.sparta.soomtut.entity.Location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoResponse {
    private Long memberId;
    private String email;
    private String nickname;
    private LocalDate createAt;
    private float starRating;
    private int level;
    private String profileImage;

    private String address;
    private float vectorX;
    private float vectorY;

    public static MemberInfoResponse toDto(Member member, Location location) {
        return MemberInfoResponse.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .createAt(member.getCreatedAt())
                .starRating(member.getStarRating())
                .level(member.getLevel())
                .profileImage(member.getImage())
                .address(location.getAddress())
                .vectorX(location.getVectorX())
                .vectorY(location.getVectorY())
                .build();
    }
    
}