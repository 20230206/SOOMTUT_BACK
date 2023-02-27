package com.sparta.soomtut.lecture.entity;

import com.sparta.soomtut.lecture.dto.request.CreateLectureRequestDto;
import com.sparta.soomtut.lecture.dto.request.UpdateLectureRequestDto;
import com.sparta.soomtut.member.entity.Member;
import com.sparta.soomtut.util.constants.Constants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Lecture {
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
    private Member tutor;

    @Column
    private Long categoryId;

    //즐겨찾기 수
    @Column(nullable = false)
    private int favorite;


    public Lecture(CreateLectureRequestDto postRequestDto, Member tutor) {
      this.title = postRequestDto.getTitle();
      this.image = postRequestDto.getImage();
      this.content = postRequestDto.getContent();
      this.fee = postRequestDto.getFee();
      this.categoryId = postRequestDto.getCategory();
      this.tutor = tutor;
    }

    public void update(UpdateLectureRequestDto updatePostRequestDto) {
      this.title = updatePostRequestDto.getTitle();
      this.image = updatePostRequestDto.getImage();
      this.content = updatePostRequestDto.getContent();
      this.fee = updatePostRequestDto.getFee();
    }

    public Lecture(String content, Long categoryId, int fee) {
        this.categoryId = categoryId;
        this.content = content;
        this.fee = fee;
        this.favorite = 0;
        this.image = Constants.STANDARD_USER_IMAGE;
    }

    public void incFavCount(){this.favorite += 1;}
    public void decFavCount(){this.favorite -= 1;}

    public Long getTutorId(){
        return tutor.getId();
    }

    public String getTutorNickname(){
        return tutor.getNickname();
    }
}
