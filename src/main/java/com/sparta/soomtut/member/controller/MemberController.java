package com.sparta.soomtut.member.controller;

import com.sparta.soomtut.util.response.ToResponse;
import com.sparta.soomtut.member.service.MemberService;
import com.sparta.soomtut.util.cookies.RefreshCookie;
import com.sparta.soomtut.util.response.SuccessCode;
import com.sparta.soomtut.util.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberController  {

    private final MemberService memberService;

    // 내 정보 불러오기
    @GetMapping(value = "/info/myinfo")
    public ResponseEntity<?> getMyInfo(
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        var data = memberService.getMemberInfo(userDetails.getMember());
        return ToResponse.of(data, SuccessCode.MEMBER_GETMYINFO_OK);
    }

    // 회원 정보 조회
    @GetMapping(value = "/info/{memberid}")
    public ResponseEntity<?> getMemberInfo(
        @PathVariable Long memberid,
        @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return ToResponse.of(null, SuccessCode.MEMBER_GETINFO_OK);
    }

    // 내 회원 정보 수정
    @PutMapping(value = "/info")
    public ResponseEntity<?> updateMyInfo(
        @RequestParam("nickname") String nickname,
        @AuthenticationPrincipal UserDetailsImpl userDetails)
    {   
        var data = memberService.updateNickname(nickname, userDetails.getMember());
        return ToResponse.of(data, SuccessCode.MEMBER_UPDATEINFO_OK);
    }

    // 회원 탈퇴
    @PutMapping(value = "/suspend")
    public ResponseEntity<?> suspendAccount(
        @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        var data = memberService.suspendAccount(userDetails.getMember().getId());
        var cookie = RefreshCookie.getCookie(null, false);
        return ToResponse.of(data, cookie, SuccessCode.MEMBER_SUSPEND_OK);
    }

    // 회원 탈퇴 취소
    @PutMapping(value = "/recover/{memberid}")
    public ResponseEntity<?> recoverAccount(
        @RequestParam Long memberid)
    {
        return ToResponse.of(null, SuccessCode.MEMBER_RECOVER_OK);
    }

}
