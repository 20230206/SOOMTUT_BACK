package com.sparta.soomtut.lectureRequest.entity;

import com.sparta.soomtut.lecture.entity.Lecture;
import com.sparta.soomtut.util.enums.LectureState;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class LectureRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private LectureState tuitionState;

    @JoinColumn(name = "post_id")
    @ManyToOne(optional = false)
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

    public LectureRequest(Lecture postId, Long tuteeId) {
        this.tuitionState = LectureState.NONE;
        this.post = postId;
        this.tuteeId = tuteeId;
        this.reviewFilter = false;
    }


    public void changeConfirmed(){
        this.tuitionState = LectureState.IN_PROGRESS;
    }

    public void changeComplete() {
        this.tuitionState = LectureState.DONE;
    }

    public void ChangTuitionReview(Lecture postId) {
        this.post = postId;
        this.reviewFilter = true;
    }
}
