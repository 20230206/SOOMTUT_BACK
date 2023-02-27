package com.sparta.soomtut.lectureRequest.repository;

import com.sparta.soomtut.lectureRequest.entity.LectureRequest;
import com.sparta.soomtut.util.enums.LectureState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LectureRequestRepository extends JpaRepository<LectureRequest,Long> {

    Optional<LectureRequest> findByPostIdAndTuteeId(Long postId, Long tuteeId);
    Optional<LectureRequest> findByPostId(Long postId);

   List<LectureRequest> findAllByTuteeIdAndTuitionState(Long TuteeId, LectureState tuitionState);

   //List<TuitionRequest> findAllByTuteeIdAndTuitionStateAndReviewFilterIsFalse(Long TutorId, Boolean reviewFilter);
   //List<TuitionRequest> findAllByTutorIdAndReviewFilter(Long TutorId, Boolean reviewFilter);
   boolean existsByPostIdAndTuteeIdAndTuitionState(Long postId, Long id, LectureState inProgress);
}
