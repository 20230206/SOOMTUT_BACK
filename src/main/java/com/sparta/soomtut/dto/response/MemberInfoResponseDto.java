package com.sparta.soomtut.dto.response;

import com.sparta.soomtut.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoResponseDto {
    private Long memberId;
    private String email;
    private String nickname;
    private LocalDate createAt;
    private float starRating;
    private int level;
    private String profileImage;

    public static MemberInfoResponseDto toDto(Member member) {
        return MemberInfoResponseDto.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .createAt(member.getCreatedAt())
                .starRating(member.getStarRating())
                .level(member.getLevel())
                .profileImage(member.getImage())
                .build();
    }
    
}
