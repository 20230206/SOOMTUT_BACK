package com.sparta.soomtut.lectureRequest.repository;

import com.sparta.soomtut.lecture.entity.Lecture;
import com.sparta.soomtut.lectureRequest.entity.LectureRequest;
import com.sparta.soomtut.util.enums.LectureState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LectureRequestRepository extends JpaRepository<LectureRequest,Long> {

    Optional<LectureRequest> findById(Long lectureRequestId);
    Optional<LectureRequest> findByLectureAndTuteeId(Lecture lecture, Long tuteeId);
    List<LectureRequest> findAllByTuteeIdAndLectureStateAndReviewFilterIsFalse(Long TuteeId, LectureState lectureState);
    Optional<LectureRequest> findByLecture(Lecture lecture);
   List<LectureRequest> findAllByTuteeIdAndLectureState(Long TuteeId, LectureState LectureState);


}







//   boolean existsByLectureAndTuteeIdAndLectureState(Lecture lecture, Long id, LectureState inProgress);
//    Optional<LectureRequest> findByPostIdAndTuteeId(Long postId, Long tuteeId);
//List<LectureRequest> findAllByTuteeIdAndTuitionStateAndReviewFilterIsFalse(Long TutorId, Boolean reviewFilter);
//List<LectureRequest> findAllByTutorIdAndReviewFilter(Long TutorId, Boolean reviewFilter);
// boolean existsByPostIdAndTuteeIdAndTuitionState(Long postId, Long id, LectureState inProgress);