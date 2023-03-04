package com.sparta.soomtut.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sparta.soomtut.review.entity.Review;

import java.util.Optional;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByLectureRequestId(Long lectureRequestId);

    @Query("Select m.nickname From Review rv Join Member m on m.id=rv.tuteeId Where rv.lectureId=:lectureId")
    List<String> findCreatorAllByLectureId(@Param("lectureId") Long lectureId);
    List<Review> findAllByLectureId(Long LectureId);
    Page<Review> findAllByTuteeIdAndDeletedIsFalse(Long memberId, Pageable pageable);
}
