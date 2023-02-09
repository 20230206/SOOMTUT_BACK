package com.sparta.soomtut.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sparta.soomtut.entity.Post;
import java.util.*;
@RestController
public class BoardController   {

    @GetMapping("/board")
    public List<Post> getMyPosts(){

        //service

        //return
        return new ArrayList<>();
    }

    @GetMapping("/boardAll")
    public List<Post> getAllPost(){

        //service

        //return
        return new ArrayList<>();
    }

    


}
