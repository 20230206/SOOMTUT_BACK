package com.sparta.soomtut.lecture.service.impl;

import com.sparta.soomtut.lecture.dto.request.CreateLectureRequestDto;
import com.sparta.soomtut.lecture.dto.response.LectureResponseDto;
import com.sparta.soomtut.lecture.entity.Category;
import com.sparta.soomtut.lecture.entity.Lecture;
import com.sparta.soomtut.lecture.repository.LectureRepository;
import com.sparta.soomtut.lecture.service.LectureService;
import com.sparta.soomtut.member.entity.Member;
import com.sparta.soomtut.member.entity.enums.MemberRole;
import com.sparta.soomtut.util.response.ErrorCode;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public static final String CLOUD_FRONT_DOMAIN_NAME = "https://d14tc44lwo36do.cloudfront.net/";

    @Override
    @Transactional(readOnly = true)
    public LectureResponseDto getLecture(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(
            () -> new IllegalArgumentException(ErrorCode.NOT_FOUND_POST.getMessage()));
        return LectureResponseDto.toDto().lecture(lecture).build();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LectureResponseDto> getMemberLecture(int category,Long memberId,Pageable pageable) {
        Page<Lecture> lectures;
        if(category == 0 ){
            lectures = this.getAllLectureByMemberId(memberId, pageable);
        }
        else {
            lectures = this.getMemberLectures(category, memberId, pageable);
        }
        return lectures.map(LectureResponseDto::new);
    }

    // 게시글 작성
    @Override
    @Transactional
    public LectureResponseDto createLecture(
            Member member,
            CreateLectureRequestDto lectureRequestDto,
            MultipartFile file)
    {
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = postdir + "/" + date.format(new Date()) + "-" + file.getOriginalFilename();
        Lecture lecture = new Lecture(lectureRequestDto,CLOUD_FRONT_DOMAIN_NAME + fileName, member);
        lectureRepository.save(lecture);
        return LectureResponseDto.toDto().lecture(lecture).build();
    }

    // 게시글 수정
    @Override
    @Transactional
    public LectureResponseDto updateLecture(Long lectureId, CreateLectureRequestDto postRequestDto, Member member,MultipartFile file) {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(
                () -> new IllegalArgumentException(ErrorCode.NOT_FOUND_POST.getMessage())
        );
        // 작성자 또는 관리자만 수정가능
        if (member.getMemberRole() != MemberRole.ADMIN) {
            if (!lecture.getTutorId().equals(member.getId()))
                throw new IllegalArgumentException(ErrorCode.AUTHORIZATION.getMessage());
        }
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss");
        System.out.println(file);


        if(file == null){
            String fileName = lecture.getImage();
            lecture.update(postRequestDto,fileName);
        }else{
            String fileName = postdir + "/" + date.format(new Date()) + "-" + file.getOriginalFilename();
            lecture.update(postRequestDto,CLOUD_FRONT_DOMAIN_NAME+fileName);
        }

        return new LectureResponseDto(lecture);
    }

    //게시글 삭제
    @Override
    @Transactional
    public void deleteLecture(Long lectureId, Member member) {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(
                () -> new IllegalArgumentException(ErrorCode.NOT_FOUND_POST.getMessage()));

        if (member.getMemberRole() == MemberRole.ADMIN) {
            lectureRepository.delete(lecture);
        }

        lectureRepository.deleteById(lectureId);
    }

    @Override
    @Transactional(readOnly = true)
    public Lecture getLectureById(Long lectureId) {
        return lectureRepository.findById(lectureId).orElseThrow(
                 () -> new IllegalArgumentException(ErrorCode.NOT_FOUND_CLASS.getMessage()));
    }

    @Override
    @Transactional
    public Long getTutorId(Long lectureId) {
        return getLectureById(lectureId).getTutorId();
    }

    @Override
    public LectureResponseDto getMyLecture(Member member) {
        Lecture lecture = lectureRepository.findByMemberId(member.getId())
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NOT_FOUND_CLASS.getMessage()));
        return LectureResponseDto.toDto().lecture(lecture).build();
    }

    @Override
    @Transactional(readOnly = true) 
    public boolean checkLectureAuthor(Long lectureId, Member member) {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(
            () -> new IllegalArgumentException(ErrorCode.NOT_FOUND_CLASS.getMessage()));
        return lecture.getTutorId().equals(member.getId());
    }

    @Override
    @Transactional(readOnly = true) 
    public Page<Lecture> getLectures(int category, Pageable pageable) {
        return lectureRepository.findAllByCategory(Category.valueOf(category), pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Lecture> getMemberLectures(int category,Long memberId ,Pageable pageable) {
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

    // TODO : 변경
    @Override
    @Transactional
    public Page<LectureResponseDto> searchByKeyword(String keyword,Pageable pageable) {
        Page<Lecture> lectures = lectureRepository.findLectureByKeyword(keyword,pageable);
        return lectures.map(item -> LectureResponseDto.toDto().lecture(item).build());
    }

    @Override
    public List<LectureResponseDto> getPopularLectures() {
        Pageable pageable = PageRequest.of(0, 9);
        List<Lecture> top9Lectures = lectureRepository.findTop9ByFavorite(pageable);
        return top9Lectures.stream()
                .map(LectureResponseDto::new)
                .collect(Collectors.toList());
    }

}
