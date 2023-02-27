package com.sparta.soomtut.lecture.service.impl;

import com.sparta.soomtut.lecture.dto.response.LectureResponseDto;
import com.sparta.soomtut.lecture.entity.Lecture;
import com.sparta.soomtut.lecture.service.BoardService;
import com.sparta.soomtut.lecture.service.LectureService;
import com.sparta.soomtut.location.service.LocationService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final LectureService postService;
    
    private final LocationService locationService;

    @Override
    @Transactional(readOnly = true)
    public Page<LectureResponseDto> getPostsByMemberId(Long memberId, Pageable pageable) {
        Page<Lecture> posts = postService.getAllLectureByMemberId(memberId, pageable);

        return posts.map(item -> new LectureResponseDto(item,
                                         item.getMember().getNickname(),
                                         locationService.getLocation(item.getMember()).getAddress()));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LectureResponseDto> getAllPost(Long category, Pageable pageable) {

        if(category == 0 ){
            Page<Lecture> posts = postService.getLectures(pageable);
            return posts.map(item -> new LectureResponseDto(item));
        }
        else {
            Page<Lecture> posts = postService.getLectures(category, pageable);
            return posts.map(item -> new LectureResponseDto(item));
        }

    }


}
