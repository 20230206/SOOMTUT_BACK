package com.sparta.soomtut.entity;

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

    private Long tutorId;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int fee;

    //즐겨찾기 수
    @Column(nullable = false)
    private int favorit;

    public Post(Long tutorId, String content, Category category, int fee) {
        this.tutorId = tutorId;
        this.category = category;
        this.content = content;
        this.fee = fee;
        //즐겨찾기 수
        this.favorit = 0;
    }

    public void increFavCount(){this.favorit += 1;}
    public void decreFavCount(){this.favorit -= 1;}
}
