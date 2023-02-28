package com.sparta.soomtut.image.controller;

import java.io.IOException;

import com.sparta.soomtut.util.security.UserDetailsImpl;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.sparta.soomtut.image.service.S3Service;

import org.springframework.http.ResponseEntity;

import com.sparta.soomtut.util.response.SuccessCode;
import com.sparta.soomtut.util.response.ToResponse;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {
    private final S3Service s3Service;

    //마이페이지 이미지 업로드
    @PostMapping(value = "/member")
    public ResponseEntity<?> profileImage(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestParam("file") MultipartFile file) throws IOException
    {
        String imgPath = s3Service.uploadProfile(userDetails.getMemberId(), file);
        return ToResponse.of(null, SuccessCode.IMG_PROFILE_OK);
    }

     //수업글 이미지 업로드
     @PostMapping("/lecture")
     public ResponseEntity<?> postImage(
             @AuthenticationPrincipal UserDetailsImpl userDetails,
             @RequestParam("file") MultipartFile file) throws IOException
     {
         String imgPath = s3Service.uploadLectureImage(userDetails.getMemberId(), file);
         return ToResponse.of(null, SuccessCode.IMG_PROFILE_OK);
     }
}
