package com.sparta.soomtut.member.dto.response;

import com.sparta.soomtut.member.entity.Member;
import com.sparta.soomtut.member.entity.enums.MemberState;

import com.sparta.soomtut.location.dto.response.LocationResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {

    private Long memberId;
    private String email;
    private String nickname;
    private LocalDate createAt;
    private float starScore;
    private String profileImage;
    private MemberState state;

    private LocationResponse location;


    @Builder(builderClassName = "MemberInfoToDto", builderMethodName = "toDto")
    public MemberResponse(Member member) {
        this.memberId = member.getId();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.createAt = member.getCreatedAt();
        this.starScore = member.getStarScore();
        this.profileImage = member.getImage();
        this.state = member.getState();

        this.location = LocationResponse.toDto().location(member.getLocation()).build();
    }
}
