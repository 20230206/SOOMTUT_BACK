package com.sparta.soomtut.lecture.repository;

import com.sparta.soomtut.lecture.dto.response.LectureResponseDto;
import com.sparta.soomtut.lecture.entity.Lecture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LectureRepository extends JpaRepository <Lecture, Long> {
    Optional<Lecture>findById(Long postId);
    Page<Lecture> findAll(Pageable pageable);
    
    Optional<Lecture> findByMemberId(Long memberId);
    Page<Lecture> findAllByMemberId(Long memberId, Pageable pageable);
    Page<Lecture> findAllByCategoryId(Long categoryId, Pageable pageable);

    @Query("select new com.sparta.soomtut.dto.response.LectureResponseDto(p.image,p.fee,l.address,m.nickname,p.content,p.title) from Lecture p join Member m on p.member.id = m.id join Location l on l.member.id=m.id where p.content LIKE :keyword%")
    Page<LectureResponseDto> findLectureByKeyword(String keyword, Pageable pageable);
}
