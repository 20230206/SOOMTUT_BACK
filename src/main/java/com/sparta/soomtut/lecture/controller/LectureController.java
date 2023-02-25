package com.sparta.soomtut.lecture.controller;

import com.sparta.soomtut.lecture.dto.request.CreateLectureRequestDto;
import com.sparta.soomtut.lecture.dto.request.UpdateLectureRequestDto;
import com.sparta.soomtut.lecture.dto.response.LectureResponseDto;
import com.sparta.soomtut.lecture.entity.Lecture;
import com.sparta.soomtut.lecture.service.LectureService;
import com.sparta.soomtut.member.entity.Member;
import com.sparta.soomtut.service.impl.ImageService;
import com.sparta.soomtut.service.impl.S3Service;
import com.sparta.soomtut.service.interfaces.FavMemberPostService;

import com.sparta.soomtut.dto.request.PageRequestDto;

import com.sparta.soomtut.util.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
public class LectureController {
    private final LectureService postService;
    
    @GetMapping(value ="/posts/{postId}")
    public ResponseEntity<?> getPost(
        @PathVariable Long postId,
        @AuthenticationPrincipal UserDetailsImpl userDtails
    )
    {
        LectureResponseDto data = postService.getPost(postId);
        return ResponseEntity.ok().body(data);
    }

    @PostMapping(value = "/createpost")
    public LectureResponseDto createPost(
        @RequestBody CreateLectureRequestDto postRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return postService.createPost(userDetails.getMember(), postRequestDto);
    }

    @PutMapping(value = "/updatepost/{postId}")
    public LectureResponseDto updatePost(@PathVariable Long postId, @RequestBody UpdateLectureRequestDto updatePostRequestDto , @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.updatePost(postId, updatePostRequestDto, userDetails.getMember());
    }

    @DeleteMapping(value = "/deletepost/{postId}")
    public void deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePost(postId, userDetails.getMember());
    }

    @GetMapping("/post")
    public ResponseEntity<LectureResponseDto> getMyPost(@AuthenticationPrincipal UserDetailsImpl userDetails){
        Member member = userDetails.getMember();
        //return ResponseEntity.status(HttpStatus.OK).body(postService.getMyPost(userDetails.getMemberId()));
        return ResponseEntity.status(HttpStatus.OK).body(postService.getMyPost(member));
    }

    @GetMapping("/posts/{postId}/ismypost")
    public ResponseEntity<?> isMyPost(
        @PathVariable Long postId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        boolean isMyPost = postService.isMyPost(postId, userDetails.getMember());
        return ResponseEntity.ok().body(isMyPost);
    }
    
    //키워드로 상품 검색하기
    @GetMapping("/posts")
    public Page<LectureResponseDto> searchByKeyword(@ModelAttribute PageRequestDto pageRequestDto, @RequestParam String keyword){
        return postService.searchByKeyword(keyword,pageRequestDto.toPageable());
    }
}
