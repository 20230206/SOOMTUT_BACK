package com.sparta.soomtut.controller;

import com.sparta.soomtut.service.impl.ImageService;
import com.sparta.soomtut.service.impl.S3Service;
import com.sparta.soomtut.util.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

import com.sparta.soomtut.dto.request.CreateReviewRequestDto;
import com.sparta.soomtut.dto.request.ImageRequest;
import com.sparta.soomtut.dto.response.ImageResponse;
import com.sparta.soomtut.dto.request.PageRequestDto;
import com.sparta.soomtut.entity.Review;
import com.sparta.soomtut.member.service.MemberService;

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
    
    private final MemberService memberService;
    private final ImageService imageService;
    private final S3Service s3Service;

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

}
