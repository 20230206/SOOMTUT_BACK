package com.sparta.soomtut.lecture.controller;

import com.sparta.soomtut.dto.request.*;
import com.sparta.soomtut.dto.response.ImageResponse;
import com.sparta.soomtut.entity.Category;
import com.sparta.soomtut.lecture.dto.request.CreateLectureRequestDto;
import com.sparta.soomtut.lecture.dto.request.UpdateLectureRequestDto;
import com.sparta.soomtut.lecture.dto.response.LectureResponseDto;
import com.sparta.soomtut.lecture.entity.Lecture;
import com.sparta.soomtut.lecture.service.LectureService;
import com.sparta.soomtut.member.entity.Member;
import com.sparta.soomtut.repository.ImageRepository;
import com.sparta.soomtut.service.impl.ImageService;
import com.sparta.soomtut.service.impl.S3Service;
import com.sparta.soomtut.service.interfaces.FavMemberPostService;


import com.sparta.soomtut.util.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class LectureController {
    private final LectureService postService;
    private final FavMemberPostService favMemberPostService;

    private final ImageService imageService;
    private final S3Service s3Service;
    private final ImageRepository imageRepository;

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
    public ResponseEntity<LectureResponseDto> getFindFavPost(@PathVariable("postId") Long id){
        return ResponseEntity.ok().body(favMemberPostService.findFavPost(id));
    }

    @GetMapping("/post")
    public ResponseEntity<LectureResponseDto> getMyPost(@AuthenticationPrincipal UserDetailsImpl userDetails){
        Member member = userDetails.getMember();
        //return ResponseEntity.status(HttpStatus.OK).body(postService.getMyPost(userDetails.getMemberId()));
        return ResponseEntity.status(HttpStatus.OK).body(postService.getMyPost(member));
    }

    // 수업 신청 필요할듯


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
    public ResponseEntity<List<Lecture>> getCompletePost(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<Lecture> postList = postService.getCompletePost(userDetails.getMember());
        return ResponseEntity.status(HttpStatus.OK).body(postList);
    }
//
//    // 후기(작성/미작성) 수업 조회
//    @GetMapping(value = "/reviewFilter")
//    public Page<Post> getReviewFilter(
//            @ModelAttribute PageRequestDto pageRequest,
//            @AuthenticationPrincipal UserDetailsImpl userDetails
//    ){
//        return postService.getReviewFilter(pageRequest, userDetails.getMember());
//    }

    @GetMapping("/posts/{postId}/ismypost")
    public ResponseEntity<?> isMyPost(
        @PathVariable Long postId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        boolean isMyPost = postService.isMyPost(postId, userDetails.getMember());
        return ResponseEntity.ok().body(isMyPost);
    }
    // 수업글 이미지 업로드
    @PostMapping("/posts/images")
    public String  postImage(ImageRequest imageRequest, MultipartFile file) throws IOException{
        String imgPath = s3Service.uploadPostImage(imageRequest.getFilePath(), file);
        imageRequest.setFilePath(imgPath);
        imageService.saveImgPost(imageRequest);
        return "redirect:/images";
    }
    // 수업글 이미지 조회
    @GetMapping("/posts/images")
    public String getPostImage(Model model){
        List<ImageResponse> imageDtoList = imageService.getList();

        model.addAttribute("imageList", imageDtoList);

        return "/images";
    }

    //키워드로 상품 검색하기
    @GetMapping("/posts")
    public Page<LectureResponseDto> searchByKeyword(@ModelAttribute PageRequestDto pageRequestDto, @RequestParam String keyword){
        return postService.searchByKeyword(keyword,pageRequestDto.toPageable());
    }
}
