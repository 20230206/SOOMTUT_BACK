package com.sparta.soomtut.dto.request;

import com.sparta.soomtut.member.entity.Member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FavPostDto {
    private Long Id;
    private Member member;


    public FavPostDto(Long Id, Member member){
        this.Id = Id;
        this.member = member;
    }

}
