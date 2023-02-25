package com.sparta.soomtut.image.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sparta.soomtut.image.dto.request.ImageRequest;
import com.sparta.soomtut.image.dto.response.ImageResponse;
import com.sparta.soomtut.image.service.ImageService;
import com.sparta.soomtut.image.service.S3Service;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;
    private final S3Service s3Service;
    
    //마이페이지 이미지 업로드
    @PostMapping(value = "/member/mypage/images")
    public String profileImage(
        @ModelAttribute ImageRequest imageDto, 
        MultipartFile file) throws IOException
    {
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

}
