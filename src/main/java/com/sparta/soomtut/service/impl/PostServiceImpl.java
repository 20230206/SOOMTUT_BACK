package com.sparta.soomtut.service.impl;

import com.sparta.soomtut.dto.request.CategoryRequestDto;
import com.sparta.soomtut.dto.request.PostRequestDto;
import com.sparta.soomtut.dto.request.UpdatePostRequestDto;
import com.sparta.soomtut.dto.response.PostResponseDto;

import com.sparta.soomtut.entity.Category;
import com.sparta.soomtut.entity.Member;
import com.sparta.soomtut.entity.Post;
import com.sparta.soomtut.entity.TuitionRequest;

import com.sparta.soomtut.util.enums.MemberRole;
import com.sparta.soomtut.util.enums.TuitionState;

import com.sparta.soomtut.util.response.ErrorCode;

import com.sparta.soomtut.repository.CategoryRepository;
import com.sparta.soomtut.repository.PostRepository;
import com.sparta.soomtut.repository.TuitionRequestRepository;

import com.sparta.soomtut.service.interfaces.PostService;
import com.sparta.soomtut.service.interfaces.LocationService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final LocationService locationService;
    private final CategoryRepository categoryRepository;
    private final TuitionRequestRepository tuitionRequestRepository;

    
    @Override
    @Transactional
    public PostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
            () -> new IllegalArgumentException(ErrorCode.NOT_FOUND_POST.getMessage()));
        return new PostResponseDto(post, locationService.findMemberLocation(post.getMember().getId()));
    }

    // 게시글 작성
    @Override
    @Transactional
    public PostResponseDto createPost(Member member, PostRequestDto postRequestDto) {
        Post post = new Post(postRequestDto, member);
        postRepository.save(post);
        return new PostResponseDto(post, locationService.findMemberLocation(member.getId()));
    }

    // 게시글 수정
    @Override
    @Transactional
    public PostResponseDto updatePost(Long postId, UpdatePostRequestDto updatePostRequestDto, Member member) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException(ErrorCode.NOT_FOUND_POST.getMessage())
        );

        // 작성자 또는 관리자만 수정가능
        if (member.getMemberRole() != MemberRole.ADMIN) {
            if (!post.getMember().getId().equals(member.getId()))
                throw new IllegalArgumentException(ErrorCode.AUTHORIZATION.getMessage());
        }

        post.update(updatePostRequestDto);
        return new PostResponseDto(post);
    }

    //게시글 삭제
    @Override
    @Transactional
    public void deletePost(Long postId, Member member) {
        Post post = postRepository.findById(postId).orElseThrow(
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
    public Post getPostById(Long postId){
       Post post = postRepository.findById(postId).orElseThrow(
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
    public PostResponseDto getMyPost(Member member) {
        Post post = postRepository.findByMemberId(member.getId())
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NOT_FOUND_CLASS.getMessage()));
        PostResponseDto postResponseDto = new PostResponseDto(post, member.getNickname(), locationService.findMemberLocation(member.getId()).getAddress());
        return postResponseDto;
    }


    //수업 확정
    @Override
    @Transactional
    public String classConfirmed(Long postId, Member member) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException(ErrorCode.NOT_FOUND_POST.getMessage())
        );

        TuitionRequest tuitionRequest = new TuitionRequest(postId, member.getId());
        if (tuitionRequest.getTutorId().equals(member.getId()));
        tuitionRequestRepository.save(tuitionRequest);
        return "수업 확정이 완료되었습니다.";

    }

    //수업 완료
    @Override
    @Transactional
    public String classComplete(Long postId, Member member) {
        Post post = postRepository.findById(postId).orElseThrow(
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
    public List<Post> getCompletePost(Member member) {
        List<TuitionRequest> tuitionRequestList = tuitionRequestRepository.findAllByTuteeIdAndTuitionState(member.getId(), TuitionState.DONE);
        List<Long> tuitionIdList = new ArrayList<>();
        for (TuitionRequest tuitionRequest : tuitionRequestList) {
            tuitionIdList.add(tuitionRequest.getPostId());
        }
        List<Post> postList = postRepository.findAllById(tuitionIdList);
        return postList;
    }

    @Override
    @Transactional(readOnly = true) 
    public boolean isMyPost(Long postId, Member member)
    {
        Post post = postRepository.findById(postId).orElseThrow(
            () -> new IllegalArgumentException(ErrorCode.NOT_FOUND_CLASS.getMessage())
        );

        return post.getMember().getId().equals(member.getId());
    }

    @Override
    @Transactional(readOnly = true) 
    public Page<Post> getPosts(Long category, Pageable pageable){
        return postRepository.findAllByCategoryId(category, pageable);
    }

    @Override
    @Transactional(readOnly = true) 
    public Page<Post> getPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true) 
    public Page<Post> getAllPostByMemberId(Long memberId,Pageable pageable) {
        return postRepository.findAllByMemberId(memberId, pageable);
    }

    @Override
    @Transactional
    public Page<PostResponseDto> searchByKeyword(String keyword,Pageable pageable) {
        return postRepository.findPostByKeyword(keyword,pageable);
    }
}
