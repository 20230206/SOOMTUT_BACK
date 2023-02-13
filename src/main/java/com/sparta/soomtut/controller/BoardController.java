package com.sparta.soomtut.controller;

import com.sparta.soomtut.dto.PostResponseDto;
import com.sparta.soomtut.service.impl.BoardServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sparta.soomtut.entity.Post;
import java.util.*;
@RestController
public class BoardController   {

    private BoardServiceImpl boardService;

    @GetMapping("/board")
    public List<Post> getMyPosts(){

        //service

        //return
        return new ArrayList<>();
    }

    @GetMapping("/boardAll")
    public ResponseEntity<List<PostResponseDto>> getAllPost(){
        return ResponseEntity.status(HttpStatus.OK).body(boardService.getAllPost());
    }

    


}
