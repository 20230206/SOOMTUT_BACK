package com.sparta.soomtut.entity;

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

    @Column
    private Long postId;


    @Column
    private Long tuteeId;


    public TuitionRequest(Long postId, Long tuteeId) {
        this.tuitionState = TuitionState.IN_PROGRESS;
        this.postId = postId;
        this.tuteeId = tuteeId;
    }


    public void changeTuitionState(Long tuteeId){
        this.tuitionState = TuitionState.DONE;
        this.tuteeId = tuteeId;
    }
}
