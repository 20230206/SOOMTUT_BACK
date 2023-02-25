package com.sparta.soomtut.lecture.service.impl;

import com.sparta.soomtut.lecture.dto.request.CategoryRequestDto;
import com.sparta.soomtut.lecture.dto.request.CreateLectureRequestDto;
import com.sparta.soomtut.lecture.dto.request.UpdateLectureRequestDto;
import com.sparta.soomtut.lecture.dto.response.LectureResponseDto;
import com.sparta.soomtut.lecture.entity.Category;
import com.sparta.soomtut.lecture.entity.Lecture;
import com.sparta.soomtut.lecture.repository.CategoryRepository;
import com.sparta.soomtut.lecture.repository.LectureRepository;
import com.sparta.soomtut.lecture.service.LectureService;
import com.sparta.soomtut.lectureRequest.entity.TuitionRequest;
import com.sparta.soomtut.lectureRequest.repository.TuitionRequestRepository;
import com.sparta.soomtut.member.entity.Member;
import com.sparta.soomtut.util.dto.request.PageRequestDto;
import com.sparta.soomtut.util.enums.MemberRole;
import com.sparta.soomtut.util.enums.TuitionState;

import com.sparta.soomtut.util.response.ErrorCode;
import com.sparta.soomtut.location.service.LocationService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService {

    private final LectureRepository postRepository;
    private final LocationService locationService;
    private final CategoryRepository categoryRepository;
    private final TuitionRequestRepository tuitionRequestRepository;

    
    @Override
    @Transactional
    public LectureResponseDto getPost(Long postId) {
        Lecture post = postRepository.findById(postId).orElseThrow(
            () -> new IllegalArgumentException(ErrorCode.NOT_FOUND_POST.getMessage()));
        return new LectureResponseDto(post, locationService.findMemberLocation(post.getMember().getId()));
    }

    // 게시글 작성
    @Override
    @Transactional
    public LectureResponseDto createLecture(Member member, CreateLectureRequestDto postRequestDto) {
        Lecture post = new Lecture(postRequestDto, member);
        postRepository.save(post);
        return new LectureResponseDto(post, locationService.findMemberLocation(member.getId()));
    }

    // 게시글 수정
    @Override
    @Transactional
    public LectureResponseDto updateLecture(Long postId, UpdateLectureRequestDto updatePostRequestDto, Member member) {
        Lecture post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException(ErrorCode.NOT_FOUND_POST.getMessage())
        );

        // 작성자 또는 관리자만 수정가능
        if (member.getMemberRole() != MemberRole.ADMIN) {
            if (!post.getMember().getId().equals(member.getId()))
                throw new IllegalArgumentException(ErrorCode.AUTHORIZATION.getMessage());
        }

        post.update(updatePostRequestDto);
        return new LectureResponseDto(post);
    }

    //게시글 삭제
    @Override
    @Transactional
    public void deleteLecture(Long postId, Member member) {
        Lecture post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException(ErrorCode.NOT_FOUND_POST.getMessage())
        );

        if (member.getMemberRole() == MemberRole.ADMIN)
            postRepository.delete(post);

        postRepository.deleteById(postId);
    }

    //카테고리 생성
    public String createCategory(CategoryRequestDto categoryRequestDto) {
        Category category = new Category(categoryRequestDto);

        categoryRepository.save(category);

        return "카테고리 저장완료";
    }

    public List<Category> getCategory() {
        List<Category> category = categoryRepository.findAllBy();
        return category;
    }

    @Override
    @Transactional
    public Lecture getPostById(Long postId){
       Lecture post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException(ErrorCode.NOT_FOUND_CLASS.getMessage())
        );

        return post;
    }
    @Override
    @Transactional
    public Long getTutorId(Long postId) {

        return getPostById(postId).getMember().getId();

    }

    @Override
    public LectureResponseDto getMyPost(Member member) {
        Lecture post = postRepository.findByMemberId(member.getId())
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NOT_FOUND_CLASS.getMessage()));
        LectureResponseDto postResponseDto = new LectureResponseDto(post, member.getNickname(), locationService.findMemberLocation(member.getId()).getAddress());
        return postResponseDto;
    }


    //수업 확정
    @Override
    @Transactional
    public String classConfirmed(Long postId, Member member) {
        Lecture post = getPostById(postId);


        boolean isExistsRequest = tuitionRequestRepository.existsByPostIdAndTuteeIdAndTuitionState(postId, member.getId(), TuitionState.NONE);
        if(isExistsRequest) return "수업 확정이 완료되었습니다.";

        TuitionRequest tuitionRequest = new TuitionRequest(post, member.getId());
        
        tuitionRequestRepository.save(tuitionRequest);
        return "수업 확정이 완료되었습니다.";

    }



    //수업 완료
    @Override
    @Transactional
    public String classComplete(Long postId, Member member) {
        Lecture post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException(ErrorCode.NOT_FOUND_POST.getMessage())
        );
        TuitionRequest tuitionRequest = tuitionRequestRepository.findByPostId(postId).orElseThrow(
                () -> new IllegalArgumentException("Error")
        );

        tuitionRequest.changeTuitionState(member.getId());
        return "수업이 완료되었습니다.";
    }

    // 완료한 수업 목록 조회
    @Override
    @Transactional
    public List<Lecture> getCompletePost(Member member) {
        List<TuitionRequest> tuitionRequestList = tuitionRequestRepository.findAllByTuteeIdAndTuitionState(member.getId(), TuitionState.DONE);
        List<Lecture> postList = tuitionRequestList.stream().map((item) -> item.getPost()).collect(Collectors.toList());
        return postList;
    }

//    @Override
//    @Transactional
//    public Page<Post> getReviewFilter(PageRequestDto pageRequestDto, Member member) {
//        List<TuitionRequest> tuitionRequestList = tuitionRequestRepository.findAllByTuteeIdAndTuitionStateAndReviewFilterIsFalse(member.getId(), Boolean.FALSE);
//        List<Post> postReviewList = tuitionRequestList.stream().map((item) -> item.getPost()).collect(Collectors.toList());
//
//    }


    @Override
    @Transactional(readOnly = true) 
    public boolean checkLectureAuthor(Long postId, Member member)
    {
        Lecture post = postRepository.findById(postId).orElseThrow(
            () -> new IllegalArgumentException(ErrorCode.NOT_FOUND_CLASS.getMessage())
        );

        return post.getMember().getId().equals(member.getId());
    }

    @Override
    @Transactional(readOnly = true) 
    public Page<Lecture> getPosts(Long category, Pageable pageable){
        return postRepository.findAllByCategoryId(category, pageable);
    }

    @Override
    @Transactional(readOnly = true) 
    public Page<Lecture> getPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true) 
    public Page<Lecture> getAllPostByMemberId(Long memberId,Pageable pageable) {
        return postRepository.findAllByMemberId(memberId, pageable);
    }

    @Override
    @Transactional
    public Page<LectureResponseDto> searchByKeyword(String keyword,Pageable pageable) {
        return postRepository.findLectureByKeyword(keyword,pageable);

    }
}
