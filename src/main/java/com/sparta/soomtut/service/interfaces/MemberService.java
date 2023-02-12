package com.sparta.soomtut.service.interfaces;

import com.sparta.soomtut.dto.SigninRequestDto;
import com.sparta.soomtut.entity.Member;


public interface MemberService {

    
    // Repository 지원 함수
    Member findMemberByEmail(String email);
    Member saveMember(Member member);
    boolean existsMemberByEmail(String email);
    boolean existsMemberByNickname(String nickname);

}
