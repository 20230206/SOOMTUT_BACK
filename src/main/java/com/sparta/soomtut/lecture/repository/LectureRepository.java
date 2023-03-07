package com.sparta.soomtut.lecture.repository;

import com.sparta.soomtut.lecture.dto.response.LectureResponseDto;
import com.sparta.soomtut.lecture.entity.Lecture;
import com.sparta.soomtut.lecture.entity.Category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LectureRepository extends JpaRepository <Lecture, Long> {

    Optional<Lecture>findById(Long postId);
    Page<Lecture> findAll(Pageable pageable);
    Optional<Lecture> findByMemberId(Long memberId);
    Page<Lecture> findAllByMemberId(Long memberId, Pageable pageable);
    Page<Lecture> findAllByCategory(Category category, Pageable pageable);
    @Query("select l from Lecture l where l.category= :category and l.member.id=:memberId")
    Page<Lecture> findAllByCategoryAndMemberId(@Param("category") Category category, @Param("memberId")Long memberId, Pageable pageable);

    // TODO: 변경
     @Query("select p from Lecture p join Member m on p.member.id = m.id join Location l on l.id=m.location.id where p.content LIKE :keyword%")
     Page<Lecture> findLectureByKeyword(String keyword, Pageable pageable);
    @Query("select l from Lecture l ORDER BY l.favorite DESC, l.createdAt DESC")
    List<Lecture> findTop9ByFavorite(Pageable pageable);

}
