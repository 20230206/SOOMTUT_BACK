package com.sparta.soomtut.lecture.service.impl;

import com.sparta.soomtut.lecture.dto.response.LectureResponseDto;
import com.sparta.soomtut.lecture.entity.Bookmark;
import com.sparta.soomtut.lecture.entity.Lecture;
import com.sparta.soomtut.lecture.repository.BookmarkRepository;
import com.sparta.soomtut.lecture.service.BookmarkService;
import com.sparta.soomtut.lecture.service.LectureService;
import com.sparta.soomtut.member.entity.Member;
import com.sparta.soomtut.util.response.ErrorCode;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {
    private final BookmarkRepository favMemberPostRepository;
    private final LectureService postService;

    @Transactional
    @Override
    public boolean getState(Long lectureId, Member member) {
        if (!hasFavPost(lectureId, member)) {
            return false;
        }
        else {
            Bookmark fav = findByLectureIdAndMemberId(lectureId, member.getId());
            return fav.isStatus();
        }
    }
    
    //즐겨찾기 특정 Id 조회
    @Transactional
    @Override
    public LectureResponseDto findFavPost(Long id) {
        Lecture lecture = postService.getLectureById(id);
        Bookmark favMemberPost = favMemberPostRepository.findByLectureId(lecture.getId())
                .orElseThrow(()-> new IllegalArgumentException(ErrorCode.NOT_FOUND_FAVPOST.getMessage()));
        return new LectureResponseDto(favMemberPost.getLecture());
    }

    //즐겨찾기 전체 조회
    @Transactional
    @Override
    public Page<LectureResponseDto> getLecturesByBookmarked(Pageable pageable, Member member) {
        Page<Bookmark> favList = favMemberPostRepository.findAllByMemberIdAndStatusIsTrue(member.getId(), pageable);
        return favList.map((item) -> new LectureResponseDto(item.getLecture()));
    }

    //즐겨찾기 업데이트
    @Transactional
    @Override
    public boolean updateBookmark(Long lectureId, Member member) {
        /* 1. existsByPostIdAndMemberId를 통해서 return이 false 일시
          -> 새로운 FavMemberPost를 생성하고, save 해주고 return true
         2. existsByPostIdAndMemberId를 통해서 return이 true 일시
          -> findByPostIdAndMemberId 통해 해당 기록을 가져오고, 해당기록의 내용을 반대로 변경시켜준다.*/
        if(!hasFavPost(lectureId, member)) {
            Lecture lecture = postService.getLectureById(lectureId);
            Bookmark data = createFavPost(lecture, member);
            return data.isStatus();
        }
        else {
            Bookmark data = findByLectureIdAndMemberId(lectureId, member.getId());
            data.updateState(!data.isStatus());
            return data.isStatus();
        }
    }

    //즐겨찾기 추가
    @Transactional
    public Bookmark createFavPost(Lecture lecture, Member member) {
        return favMemberPostRepository.save(Bookmark.builder().lecture(lecture).member(member).build());
    }

    //글과 멤버의 값을 가지고 있다면 true 아니라면 false 용도의 함수
    @Transactional(readOnly = true)
    public boolean hasFavPost(Long lectureId, Member member) {
        //Optional 값을 가지고 있다면 ture 아니면 false ->existsBy로 변경
        return favMemberPostRepository.existsByLectureIdAndMemberId(lectureId, member.getId());
    }

    @Transactional(readOnly = true)
    public Bookmark findByLectureIdAndMemberId(Long lectureId, Long memberId) {
        return favMemberPostRepository.findByLectureIdAndMemberId(lectureId, memberId).orElseThrow(
            () -> new IllegalArgumentException("로그가 존재하지 않습니다."));
    }

}
