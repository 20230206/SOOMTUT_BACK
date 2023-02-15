package com.sparta.soomtut.dto.response;


import com.sparta.soomtut.entity.Category;
import com.sparta.soomtut.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostResponseDto {
    private String title;
    private String content;
    private Category category;
    // 임시
    private String image;
    private int fee;
    private String tutorNickname;
    private String location;


    public PostResponseDto(Post post) {
        this.title = post.getTitle();
        this.image = post.getImage();
        this.content = post.getContent();
        this.fee = post.getFee();
    }

    public PostResponseDto(Post post, String nickName, String location) {
        this.image = post.getImage();
        this.fee = post.getFee();
        this.tutorNickname = nickName ;
        this.location = location;
    }

    public PostResponseDto(PostResponseDto postResponseDto) {
        this.image = postResponseDto.image;
        this.fee = postResponseDto.fee;
        this.location = postResponseDto.location;
        this.tutorNickname = postResponseDto.tutorNickname;
    }
}
