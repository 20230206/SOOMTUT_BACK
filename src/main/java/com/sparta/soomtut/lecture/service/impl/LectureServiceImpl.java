package com.sparta.soomtut.lecture.service.impl;

import com.sparta.soomtut.lecture.dto.request.CreateLectureRequestDto;
import com.sparta.soomtut.lecture.dto.request.UpdateLectureRequestDto;
import com.sparta.soomtut.lecture.dto.response.LectureResponseDto;
import com.sparta.soomtut.lecture.entity.Category;
import com.sparta.soomtut.lecture.entity.Lecture;
import com.sparta.soomtut.lecture.repository.LectureRepository;
import com.sparta.soomtut.lecture.service.LectureService;
import com.sparta.soomtut.lectureRequest.entity.LectureRequest;
import com.sparta.soomtut.lectureRequest.repository.LectureRequestRepository;
import com.sparta.soomtut.member.entity.Member;
import com.sparta.soomtut.member.entity.enums.MemberRole;
import com.sparta.soomtut.util.enums.LectureState;

import com.sparta.soomtut.util.response.ErrorCode;
import com.sparta.soomtut.location.service.LocationService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService {

    @Value("${cloud.aws.s3.postdir}")
    private String postdir;
    private final LectureRepository lectureRepository;
    private final LocationService locationService;
    private final LectureRequestRepository lectureRequestRepository;
    public static final String CLOUD_FRONT_DOMAIN_NAME = "https://d14tc44lwo36do.cloudfront.net/";

    @Override
    @Transactional
    public LectureResponseDto getLecture(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(
            () -> new IllegalArgumentException(ErrorCode.NOT_FOUND_POST.getMessage()));
        return new LectureResponseDto(lecture, locationService.findMemberLocation(lecture.getTutorId()));
    }

    @Override
    @Transactional
    public Page<LectureResponseDto> getMemberLecture(int category,Long memberId,Pageable pageable) {

        if(category == 0 ){
            Page<Lecture> lectures = this.getAllLectureByMemberId(memberId,pageable);
            return lectures.map(item -> new LectureResponseDto(item));
        }
        else {
            Page<Lecture> lectures = this.getMemberLectures(category, memberId,pageable);
            return lectures.map(item -> new LectureResponseDto(item));
        }

    }

    // 게시글 작성
    @Override
    @Transactional
    public LectureResponseDto createLecture(Member member, CreateLectureRequestDto lectureRequestDto, MultipartFile file) {
        SimpleDateFormat date = new SimpleDateFormat("yyyymmddHHmmss");
        String fileName = postdir + "/" + date.format(new Date()) + "-" + file.getOriginalFilename();
        Lecture lecture = new Lecture(lectureRequestDto,CLOUD_FRONT_DOMAIN_NAME + fileName, member);
        lectureRepository.save(lecture);
        return new LectureResponseDto(lecture, locationService.findMemberLocation(member.getId()));
    }

    // 게시글 수정
    @Override
    @Transactional
    public LectureResponseDto updateLecture(Long lectureId, UpdateLectureRequestDto lectureRequestDto, Member member) {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(
                () -> new IllegalArgumentException(ErrorCode.NOT_FOUND_POST.getMessage())
        );

        // 작성자 또는 관리자만 수정가능
        if (member.getMemberRole() != MemberRole.ADMIN) {
            if (!lecture.getTutorId().equals(member.getId()))
                throw new IllegalArgumentException(ErrorCode.AUTHORIZATION.getMessage());
        }

        lecture.update(lectureRequestDto);
        return new LectureResponseDto(lecture);
    }

    //게시글 삭제
    @Override
    @Transactional
    public void deleteLecture(Long lectureId, Member member) {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(
                () -> new IllegalArgumentException(ErrorCode.NOT_FOUND_POST.getMessage())
        );

        if (member.getMemberRole() == MemberRole.ADMIN)
            lectureRepository.delete(lecture);

        lectureRepository.deleteById(lectureId);
    }


    @Override
    @Transactional
    public Lecture getLectureById(Long lectureId){
       Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(
                () -> new IllegalArgumentException(ErrorCode.NOT_FOUND_CLASS.getMessage())
        );

        return lecture;
    }

    @Override
    @Transactional
    public Long getTutorId(Long lectureId) {
        return getLectureById(lectureId).getTutorId();
    }

    // 이상한데, 멤버 Id 로 수업을 찾아오면 수업이 여러개 있을 수 있는거 아닌가?
    @Override
    public LectureResponseDto getMyLecture(Member member) {
        Lecture lecture = lectureRepository.findByMemberId(member.getId())
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NOT_FOUND_CLASS.getMessage()));
        LectureResponseDto lectureResponseDto = 
                new LectureResponseDto(lecture, member.getNickname(), locationService.findMemberLocation(member.getId()).getAddress());
        return lectureResponseDto;
    }



    @Override
    @Transactional(readOnly = true) 
    public boolean checkLectureAuthor(Long lectureId, Member member)
    {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(
            () -> new IllegalArgumentException(ErrorCode.NOT_FOUND_CLASS.getMessage())
        );

        return lecture.getTutorId().equals(member.getId());
    }

    @Override
    @Transactional(readOnly = true) 
    public Page<Lecture> getLectures(int category, Pageable pageable){
        return lectureRepository.findAllByCategory(Category.valueOf(category), pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Lecture> getMemberLectures(int category,Long memberId ,Pageable pageable){
        return lectureRepository.findAllByCategoryAndMemberId(Category.valueOf(category),memberId, pageable);
    }

    @Override
    @Transactional(readOnly = true) 
    public Page<Lecture> getLectures(Pageable pageable) {
        return lectureRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true) 
    public Page<Lecture> getAllLectureByMemberId(Long memberId, Pageable pageable) {
        return lectureRepository.findAllByMemberId(memberId, pageable);
    }

    @Override
    @Transactional
    public Page<LectureResponseDto> searchByKeyword(String keyword,Pageable pageable) {
        return lectureRepository.findLectureByKeyword(keyword,pageable);

    }

}
