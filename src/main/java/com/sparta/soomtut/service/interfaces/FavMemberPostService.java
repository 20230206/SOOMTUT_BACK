package com.sparta.soomtut.service.interfaces;

import com.sparta.soomtut.entity.Member;

public interface FavMemberPostService {
    boolean updateOfFavPost(Long postId, Member member);
    boolean getState(Long postId, Member member);
}
