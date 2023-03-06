package com.sparta.soomtut.member.dto.response;

import com.sparta.soomtut.location.entity.Location;
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
public class MemberInfoResponse {

    private Long memberId;
    private String email;
    private String nickname;
    private LocalDate createAt;
    private float starRating;
    private int level;
    private String profileImage;
    private MemberState state;

    private LocationResponse location;


    @Builder(builderClassName = "MemberInfoToDto", builderMethodName = "toDto")
    public MemberInfoResponse(Member member) {
        this.memberId = member.getId();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.createAt = member.getCreatedAt();
        this.starRating = member.getStarRating();
        this.level = member.getLevel();
        this.profileImage = member.getImage();
        this.state = member.getState();

        this.location = LocationResponse.toDto().location(member.getLocation()).build();
    }
}
