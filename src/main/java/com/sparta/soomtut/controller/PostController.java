package com.sparta.soomtut.controller;

import com.sparta.soomtut.dto.PostResponseDto;
import com.sparta.soomtut.entity.Member;
import com.sparta.soomtut.service.impl.BoardServiceImpl;
import com.sparta.soomtut.service.impl.PostServiceImpl;
import com.sparta.soomtut.util.security.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class PostController {

    private PostServiceImpl postService;

    @PostMapping(value = "/createpost")
    public void CreatePost() {
        {
            // Service

            // return
        }

    }

    @PutMapping(value = "/updatepost")
    public void updatePost() {
        {
            // Service

            // return
        }
    }

    @DeleteMapping(value = "/deletepost")
    public void deletePost() {
        {
            // Service

            // return
        }
    }

    @PostMapping(value = "/bookmark")
    public void bookMark() {
        {
            // Service

            // return
        }
    }

    @GetMapping("/post")
    public ResponseEntity<PostResponseDto> getMyPost(@AuthenticationPrincipal UserDetailsImpl userDetails){
        Member member = userDetails.getMember();
        //return ResponseEntity.status(HttpStatus.OK).body(postService.getMyPost(userDetails.getMemberId()));
        return ResponseEntity.status(HttpStatus.OK).body(postService.getMyPost(member));
    }
}
