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
   
    private final FavMemberPostService favMemberPostService;

}
