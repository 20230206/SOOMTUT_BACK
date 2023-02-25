package com.sparta.soomtut.lectureRequest.entity;

import com.sparta.soomtut.lecture.entity.Lecture;
import com.sparta.soomtut.util.enums.TuitionState;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class TuitionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TuitionState tuitionState;

    @JoinColumn(name = "post_Id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Lecture post;

    @Column
    private Long tuteeId;

    @Column
    private Boolean reviewFilter;


//    public TuitionRequest(Long postId, Long tuteeId, Long tutorId) {
//        this.tuitionState = TuitionState.IN_PROGRESS;
//        this.postId = postId;
//        this.tuteeId = tuteeId;
//        this.tutorId = tutorId;
//    }

    public TuitionRequest(Lecture postId, Long tuteeId) {
        this.tuitionState = TuitionState.NONE;
        this.post = postId;
        this.tuteeId = tuteeId;
        this.reviewFilter = false;
    }


    public void changeTuitionState(Long tuteeId){
        this.tuitionState = TuitionState.DONE;
        this.tuteeId = tuteeId;
    }

    public void ChangTuitionReview(Lecture postId) {
        this.post = postId;
        this.reviewFilter = true;
    }
}
