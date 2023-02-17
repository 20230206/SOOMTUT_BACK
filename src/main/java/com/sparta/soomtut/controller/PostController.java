package com.sparta.soomtut.controller;

import com.sparta.soomtut.dto.request.CategoryRequestDto;
import com.sparta.soomtut.entity.Category;
import com.sparta.soomtut.entity.Member;
import com.sparta.soomtut.dto.request.FavRequestDto;
import com.sparta.soomtut.dto.request.PostRequestDto;
import com.sparta.soomtut.dto.request.UpdatePostRequestDto;
import com.sparta.soomtut.dto.response.PostResponseDto;
import com.sparta.soomtut.service.interfaces.PostService;
import com.sparta.soomtut.service.interfaces.FavMemberPostService;


import com.sparta.soomtut.util.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final FavMemberPostService favMemberPostService;

    @GetMapping(value ="/posts/{postId}") 
    public ResponseEntity<?> getPost(
        @PathVariable Long postId,
        @AuthenticationPrincipal UserDetailsImpl userDtails
    )
    {
        PostResponseDto data = postService.getPost(postId);
        return ResponseEntity.ok().body(data);
    }

    @PostMapping(value = "/createpost")
    public PostResponseDto createPost(
        @RequestBody PostRequestDto postRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return postService.createPost(userDetails.getMember(), postRequestDto);
    }

    @PutMapping(value = "/updatepost/{postId}")
    public PostResponseDto updatePost(@PathVariable Long postId, @RequestBody UpdatePostRequestDto updatePostRequestDto , @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.updatePost(postId, updatePostRequestDto, userDetails.getMember());
    }

    @DeleteMapping(value = "/deletepost/{postId}")
    public void deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePost(postId, userDetails.getMember());
    }

    //카테고리 생성
    //TODO: 관리자만 변경되도록 수정
    @PostMapping(value = "/createCategory")
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        String category = postService.createCategory(categoryRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(category);
    }


    @GetMapping(value = "/getCategory")
    public ResponseEntity<List<Category>> getCategory() {
        List<Category> category = postService.getCategory();
        return ResponseEntity.status(HttpStatus.OK).body(category);
    }

    @GetMapping(value = "/posts/{postId}/bookmark")
    public ResponseEntity<?> getBookmarkState(
        @PathVariable Long postId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    )
    {
        var data = favMemberPostService.getState(postId, userDetails.getMember());
        return ResponseEntity.ok().body(data);
    }

    //즐겨찾기 추가 및 취소
    @PostMapping(value = "/posts/{postId}/bookmark")
    public ResponseEntity<?> bookMark(
        @PathVariable Long postId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        var data = favMemberPostService.updateOfFavPost(postId, userDetails.getMember());
        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/post")
    public ResponseEntity<PostResponseDto> getMyPost(@AuthenticationPrincipal UserDetailsImpl userDetails){
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
}
