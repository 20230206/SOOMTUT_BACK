package com.sparta.soomtut.entity;

import com.sparta.soomtut.dto.request.PostRequestDto;
import com.sparta.soomtut.dto.request.UpdatePostRequestDto;
import com.sparta.soomtut.util.enums.TuitionState;
import com.sparta.soomtut.util.constants.Constants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String image;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int fee;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column
    private Long categoryId;

    //즐겨찾기 수
    @Column(nullable = false)
    private int favorit;


    public Post(PostRequestDto postRequestDto, Member member) {
      this.title = postRequestDto.getTitle();
      this.image = postRequestDto.getImage();
      this.content = postRequestDto.getContent();
      this.fee = postRequestDto.getFee();
      this.categoryId = postRequestDto.getCategory();
      this.member = member;
    }

    public void update(UpdatePostRequestDto updatePostRequestDto) {
      this.title = updatePostRequestDto.getTitle();
      this.image = updatePostRequestDto.getImage();
      this.content = updatePostRequestDto.getContent();
      this.fee = updatePostRequestDto.getFee();
    }

    public Post(String content, Long categoryId, int fee) {
        this.categoryId = categoryId;
        this.content = content;
        this.fee = fee;
        this.favorit = 0;
        this.image = Constants.STANDARD_USER_IMAGE;
    }

    public void increFavCount(){this.favorit += 1;}
    public void decreFavCount(){this.favorit -= 1;}
}
