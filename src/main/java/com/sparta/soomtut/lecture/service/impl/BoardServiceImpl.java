package com.sparta.soomtut.lecture.service.impl;

import com.sparta.soomtut.lecture.dto.response.LectureResponseDto;
import com.sparta.soomtut.lecture.entity.Lecture;
import com.sparta.soomtut.lecture.service.BoardService;
import com.sparta.soomtut.lecture.service.LectureService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final LectureService lectureService;

    @Override
    @Transactional(readOnly = true)
    public Page<LectureResponseDto> getLecturesByMemberId(Long memberId, Pageable pageable) {
        Page<Lecture> lecture = lectureService.getAllLectureByMemberId(memberId, pageable);
        return lecture.map(item -> LectureResponseDto.toDto().lecture(item).build());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LectureResponseDto> getAllPost(int category, Pageable pageable) {
        Page<Lecture> lectures;

        if(category == 0 ){
            lectures = lectureService.getLectures(pageable);
        }
        else {
            lectures = lectureService.getLectures(category, pageable);
        }

        return lectures.map(item -> LectureResponseDto.toDto().lecture(item).build());
    }

}
