package com.sparta.soomtut.controller;


import lombok.RequiredArgsConstructor;

import com.sparta.soomtut.auth.service.*;
import com.sparta.soomtut.entity.*;
import com.sparta.soomtut.lecture.service.*;
import com.sparta.soomtut.lecture.dto.request.*;
import com.sparta.soomtut.lecture.dto.response.*;
import com.sparta.soomtut.lecture.entity.*;
import com.sparta.soomtut.service.interfaces.*;
import com.sparta.soomtut.service.impl.*;
import com.sparta.soomtut.member.service.*;

import com.sparta.soomtut.dto.request.*;
import com.sparta.soomtut.dto.response.*;
import com.sparta.soomtut.util.security.*;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import org.springframework.ui.Model;

import org.springframework.web.multipart.MultipartFile;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class TempController {
    
    private final LectureService postService;
    private final MemberService memberService;
    private final ImageService imageService;
    private final S3Service s3Service;
    private final FavMemberPostService favMemberPostService;


    // 리뷰 생성
    @PostMapping(value = "/board/{postId}")
    public ResponseEntity<?> createReview(
            @RequestParam Long postId,
            @RequestBody CreateReviewRequestDto reviewRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            /* UserDetails */
    ) {
        // Service
        String msg = memberService.createReview(postId,reviewRequestDto,userDetails.getMember());
        // return

        return ResponseEntity.status(HttpStatus.CREATED).body(msg);
    }

    //리뷰 조회
    @GetMapping(value = "/review")
    public Page<Review> getReview(@ModelAttribute PageRequestDto pageRequest, @AuthenticationPrincipal UserDetailsImpl userDetails){

        return memberService.getReview(pageRequest,userDetails.getMember());
    }

    //리뷰 삭제
    @PostMapping(value = "/review/{reviewId}")
    public ResponseEntity<?> deleteReviewRequest(
            @PathVariable Long reviewId

    ){
        String msg = memberService.deleteReviewRequest(reviewId);
        return ResponseEntity.ok().body(msg);
    }

    //마이페이지 이미지 업로드
    @PostMapping(value = "/member/mypage/images")
    public String profileImage(@ModelAttribute ImageRequest imageDto, MultipartFile file) throws IOException{
        String imgPath = s3Service.uploadProfile(imageDto.getFilePath(), file);
        imageDto.setFilePath(imgPath);
        imageService.saveImgPost(imageDto);
        return "redirect:/images";
    }
    //마이페이지 이미지 조회
    @GetMapping(value = "/member/mypage/images")
    public String  getImage(Model model){
        List<ImageResponse> imageDtoList = imageService.getList();

        model.addAttribute("imageList", imageDtoList);

        return "/images";
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


    // 수업 신청 필요할듯


//
//    // 후기(작성/미작성) 수업 조회
//    @GetMapping(value = "/reviewFilter")
//    public Page<Post> getReviewFilter(
//            @ModelAttribute PageRequestDto pageRequest,
//            @AuthenticationPrincipal UserDetailsImpl userDetails
//    ){
//        return postService.getReviewFilter(pageRequest, userDetails.getMember());
//    }


        

    //즐겨찾기 특정 조회
    @GetMapping(value = "/bookmark/{postId}")
    public ResponseEntity<LectureResponseDto> getFindFavPost(@PathVariable("postId") Long id){
        return ResponseEntity.ok().body(favMemberPostService.findFavPost(id));
    }

}
