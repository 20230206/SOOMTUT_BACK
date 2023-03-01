package com.sparta.soomtut.lectureRequest.repository;

import com.sparta.soomtut.lecture.entity.Lecture;
import com.sparta.soomtut.lectureRequest.entity.LectureRequest;
import com.sparta.soomtut.util.enums.LectureState;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LectureRequestRepository extends JpaRepository<LectureRequest,Long> {

    Optional<LectureRequest> findById(Long lectureRequestId);
    Optional<LectureRequest> findByLectureAndTuteeId(Lecture lecture, Long tuteeId);
    //List<LectureRequest> findAllByTuteeIdAndLectureStateAndReviewFilterIsFalse(Long TuteeId, LectureState lectureState);

    @Query("SELECT lr FROM LectureRequest lr WHERE lr.tuteeId = :tuteeId AND lr.lectureState = 'DONE' AND lr.reviewFilter = false")
    Page<LectureRequest> findAllByTuteeIdByAndStateIsDoneAndFalse(@Param("tuteeId") Long tuteeId);

    @Query("SELECT lr FROM LectureRequest lr WHERE lr.tuteeId = :tuteeId AND lr.lectureState = 'DONE'")
    Page<LectureRequest> findAllByTuteeIdByAndStateIsDone(@Param("tuteeId") Long tuteeId);

    Optional<LectureRequest> findByLecture(Lecture lecture);
   List<LectureRequest> findAllByTuteeIdAndLectureState(Long TuteeId, LectureState LectureState);

    @Query("SELECT COUNT(lr) > 0 FROM LectureRequest lr WHERE lr.tuteeId = :tuteeId AND lr.lecture.id = :lectureId")
    boolean existsByTuteeIdAndLectureId(@Param("tuteeId") Long tuteeId, @Param("lectureId") Long lectureId);

}







//   boolean existsByLectureAndTuteeIdAndLectureState(Lecture lecture, Long id, LectureState inProgress);
//    Optional<LectureRequest> findByPostIdAndTuteeId(Long postId, Long tuteeId);
//List<LectureRequest> findAllByTuteeIdAndTuitionStateAndReviewFilterIsFalse(Long TutorId, Boolean reviewFilter);
//List<LectureRequest> findAllByTutorIdAndReviewFilter(Long TutorId, Boolean reviewFilter);
// boolean existsByPostIdAndTuteeIdAndTuitionState(Long postId, Long id, LectureState inProgress);