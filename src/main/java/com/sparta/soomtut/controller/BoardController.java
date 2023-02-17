package com.sparta.soomtut.controller;

import com.sparta.soomtut.dto.response.PostResponseDto;
import com.sparta.soomtut.service.impl.BoardServiceImpl;
import com.sparta.soomtut.util.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
@RestController
@RequiredArgsConstructor
public class BoardController   {

    private final BoardServiceImpl boardService;

    @GetMapping("/board/myposts")
    public ResponseEntity<List<PostResponseDto>> getMyPosts(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.status(HttpStatus.OK).body(boardService.getMyPosts(userDetails.getMember().getId()));
    }

    @GetMapping("/boardAll")
    public ResponseEntity<List<PostResponseDto>> getAllPost(
        @RequestParam(required = false, value = "category") Long category
    ){
        System.out.println(category);
        List<PostResponseDto> data = new ArrayList<PostResponseDto>();
        if(category == 0) data = boardService.getAllPost();
        else data = boardService.getAllPost(category);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    


}
