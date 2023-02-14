package com.sparta.soomtut.controller;

import com.sparta.soomtut.dto.request.PostRequestDto;
import com.sparta.soomtut.dto.request.UpdatePostRequestDto;
import com.sparta.soomtut.dto.response.PostResponseDto;
import com.sparta.soomtut.service.interfaces.PostService;
import com.sparta.soomtut.util.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;

import com.sparta.soomtut.dto.FavPostDto;

import com.sparta.soomtut.service.interfaces.FavMemberPostService;
import com.sparta.soomtut.util.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    private final FavMemberPostService favMemberPostService;

    @PostMapping(value = "/createpost")
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
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

    //즐겨찾기 추가 및 취소
    @PostMapping(value = "/bookmark")
    public ResponseEntity<FavPostDto> bookMark(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        FavPostDto favPostDto = new FavPostDto(postId,userDetails.getMember());
        return ResponseEntity.ok().body(favPostDto);
    }

}
