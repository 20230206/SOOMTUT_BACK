package com.sparta.soomtut.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PostController {

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

}
