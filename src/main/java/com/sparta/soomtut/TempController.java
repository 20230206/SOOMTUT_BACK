package com.sparta.soomtut;


import lombok.RequiredArgsConstructor;

import com.sparta.soomtut.auth.service.*;
import com.sparta.soomtut.image.dto.request.ImageRequest;
import com.sparta.soomtut.image.dto.response.ImageResponse;
import com.sparta.soomtut.image.service.ImageService;
import com.sparta.soomtut.image.service.S3Service;
import com.sparta.soomtut.lecture.service.*;
import com.sparta.soomtut.lecture.entity.*;
import com.sparta.soomtut.lecture.dto.request.*;
import com.sparta.soomtut.lecture.dto.response.*;
import com.sparta.soomtut.member.service.*;
import com.sparta.soomtut.review.dto.request.CreateReviewRequestDto;
import com.sparta.soomtut.review.entity.Review;
import com.sparta.soomtut.util.entity.*;
import com.sparta.soomtut.util.security.*;
import com.sparta.soomtut.util.dto.request.PageRequestDto;

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

//    // 후기(작성/미작성) 수업 조회
//    @GetMapping(value = "/reviewFilter")
//    public Page<Post> getReviewFilter(
//            @ModelAttribute PageRequestDto pageRequest,
//            @AuthenticationPrincipal UserDetailsImpl userDetails
//    ){
//        return postService.getReviewFilter(pageRequest, userDetails.getMember());
//    }

}
