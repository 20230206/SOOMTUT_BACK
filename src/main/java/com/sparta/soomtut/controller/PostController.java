package com.sparta.soomtut.controller;

import com.sparta.soomtut.service.interfaces.FavMemberPostService;
import com.sparta.soomtut.util.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class PostController {

    private final FavMemberPostService favMemberPostService;

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

//    @PostMapping(value = "/bookmark")
//    public void bookMark(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        {
//            favMemberPostService.updateOfFavPost(postId,userDetails.getMember());
//        }
//    }
    //즐겨찾기 추가 및 취소
    @PostMapping(value = "/bookmark")
    public ResponseEntity<String> bookMark(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return new ResponseEntity<>(favMemberPostService.updateOfFavPost(postId,userDetails.getMember()),HttpStatus.OK);
    }

}
