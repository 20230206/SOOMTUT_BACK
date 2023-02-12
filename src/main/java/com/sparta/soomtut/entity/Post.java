package com.sparta.soomtut.entity;

import com.sparta.soomtut.dto.request.UpdatePostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Component;

@Getter
@NoArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private Long tutorId;

    private String member;

    @Column
    private String image;

    @Column(nullable = false)
    private String title;

//    @ManyToOne
//    @JoinColumn(name = "category_id")
//    private Category category;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int fee;

    public Post(String member, String title, String image, String content, int fee) {
        this.member = member;
        this.title = title;
        this.image = image;
        this.content = content;
        this.fee = fee;
    }

    public void update(UpdatePostRequestDto updatePostRequestDto) {
        this.title = updatePostRequestDto.getTitle();
        this.image = updatePostRequestDto.getImage();
        this.content = updatePostRequestDto.getContent();
        this.fee = updatePostRequestDto.getFee();
    }


}
