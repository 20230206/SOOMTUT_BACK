package com.sparta.soomtut.controller;

import com.sparta.soomtut.dto.request.CategoryRequestDto;
import com.sparta.soomtut.dto.request.PageRequestDto;
import com.sparta.soomtut.entity.Category;
import com.sparta.soomtut.entity.Member;
import com.sparta.soomtut.dto.request.PostRequestDto;
import com.sparta.soomtut.dto.request.UpdatePostRequestDto;
import com.sparta.soomtut.dto.response.PostResponseDto;

import com.sparta.soomtut.entity.Post;

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
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
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
    //즐겨찾기 전체 조회
    @GetMapping(value = "/bookmark")
    public ResponseEntity<?> getFindAllFavPost(
        @ModelAttribute PageRequestDto pageRequest,
        @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        var data = favMemberPostService.findAllFavPosts(pageRequest.toPageable(), userDetails.getMember());
        return ResponseEntity.ok().body(data);
    }
    //즐겨찾기 특정 조회
    @GetMapping(value = "/bookmark/{postId}")
    public ResponseEntity<PostResponseDto> getFindFavPost(@PathVariable("postId") Long id){
        return ResponseEntity.ok().body(favMemberPostService.findFavPost(id));
    }

    @GetMapping("/post")
    public ResponseEntity<PostResponseDto> getMyPost(@AuthenticationPrincipal UserDetailsImpl userDetails){
        Member member = userDetails.getMember();
        //return ResponseEntity.status(HttpStatus.OK).body(postService.getMyPost(userDetails.getMemberId()));
        return ResponseEntity.status(HttpStatus.OK).body(postService.getMyPost(member));
    }



    // 수업 확정
    @PostMapping("/classConfirmed/{postId}")
    public ResponseEntity<?> classConfirmed(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String confiremd = postService.classConfirmed(postId, userDetails.getMember());
        return ResponseEntity.status(HttpStatus.OK).body(confiremd);
    }

    // 수업 완료
    @PutMapping("/classComplete/{postId}")
    public ResponseEntity<?> classComplete(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String complete = postService.classComplete(postId, userDetails.getMember());
        return ResponseEntity.status(HttpStatus.OK).body(complete);
    }


    // 완료된 수업 목록 조회
    @GetMapping("/getCompletePost")
    public ResponseEntity<List<Post>> getCompletePost(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<Post> postList = postService.getCompletePost(userDetails.getMember());
        return ResponseEntity.status(HttpStatus.OK).body(postList);
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
