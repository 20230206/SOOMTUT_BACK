package com.sparta.soomtut.dto.response;


import com.sparta.soomtut.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostResponseDto {
    private String title;
    private String image;
    private String content;
    private int fee;

    public PostResponseDto(Post post) {
        this.title = post.getTitle();
        this.image = post.getImage();
        this.content = post.getContent();
        this.fee = post.getFee();
    }
}
