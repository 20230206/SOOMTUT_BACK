package com.sparta.soomtut.lectureRequest.entity;

import com.sparta.soomtut.chat.entity.ChatRoom;
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
    private LectureState lectureState;


    @JoinColumn(name = "lecture_Id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Lecture lecture;

    @OneToOne(mappedBy = "lectureRequest")
    private ChatRoom chatRoom;

    @Column
    private Long tuteeId;

    @Column
    private Boolean reviewFilter;

    public LectureRequest(Lecture lecture, Long tuteeId) {
        this.lectureState = LectureState.NONE;
        this.lecture = lecture;
        this.tuteeId = tuteeId;
        this.reviewFilter = false;
    }


    public void changeComplete() {
        this.lectureState = LectureState.DONE;
    }

    public void changeConfirmed(){
        this.lectureState = LectureState.IN_PROGRESS;
    }


    public void ChangTuitionReview(Lecture lecture) {
        this.lecture = lecture;
        this.reviewFilter = true;
    }

    public Long getLectureId(){
        return lecture.getId();
    }

    public Long getTutorId(){
        return lecture.getTutorId();
    }


}
