package com.sparta.soomtut.dto.response;

import com.sparta.soomtut.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostResponseDto {
    private Long postId;
    private String title;
    private String content;
    private Long categoryId;
    private String image;
    private int fee;
    private String tutorNickname;
    private String location;

    public PostResponseDto(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.categoryId = post.getCategoryId();
        this.image = post.getImage();
        this.fee = post.getFee();
    }

    public PostResponseDto(Post post, String nickName, String location) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.categoryId = post.getCategoryId();

        this.image = post.getImage();
        this.fee = post.getFee();
        this.tutorNickname = nickName ;
        this.location = location;
    }
}
