package com.sparta.soomtut.repository;

import com.sparta.soomtut.entity.TuitionRequest;
import com.sparta.soomtut.enums.TuitionState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TuitionRequestRepository extends JpaRepository<TuitionRequest,Long> {

    Optional<TuitionRequest> findByPostIdAndTuteeId(Long postId, Long tuteeId);
    Optional<TuitionRequest> findByPostId(Long postId);

   List<TuitionRequest> findAllByTuteeIdAndTuitionState(Long TuteeId, TuitionState tuitionState);
}
