package com.sparta.soomtut.lecture.controller;

import com.sparta.soomtut.util.security.UserDetailsImpl;

import com.sparta.soomtut.dto.request.PageRequestDto;
import com.sparta.soomtut.lecture.service.impl.BoardServiceImpl;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.soomtut.util.response.SuccessCode;
import com.sparta.soomtut.util.response.ToResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController   {

    private final BoardServiceImpl boardService;

    @GetMapping("/myposts")
    public ResponseEntity<?> getMyPosts(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @ModelAttribute PageRequestDto pageable
    )
    {
        var data = boardService.getPostsByMemberId(userDetails.getMember().getId(), pageable.toPageable());

        return ToResponse.of(data, SuccessCode.MESSGE_OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllPost(
        @RequestParam(required = false, value = "category") Long category,
        @ModelAttribute PageRequestDto pageable
    ){
        var data = boardService.getAllPost(category, pageable.toPageable());
        
        return ToResponse.of(data, SuccessCode.MESSGE_OK);
    }

    


}