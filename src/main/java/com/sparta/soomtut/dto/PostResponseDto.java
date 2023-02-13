package com.sparta.soomtut.dto;

import com.sparta.soomtut.entity.Category;
import com.sparta.soomtut.entity.Post;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class PostResponseDto {


    private Category category;

    // 임시
    private String image;
    private int fee;

    private String tutorNickname;

    private String location;

    public PostResponseDto(Post post, String nickName, String location) {
        this.category = post.getCategory();
        this.image = "기본 이미지 주소";
        this.fee = post.getFee();
        this.tutorNickname = nickName ;
        this.location = location;
    }
}
