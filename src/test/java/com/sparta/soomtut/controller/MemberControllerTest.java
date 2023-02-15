package com.sparta.soomtut.controller;

import com.google.gson.Gson;
import com.sparta.soomtut.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)

class MemberControllerTest {

    @Mock
    MemberServiceImpl memberService;

    @InjectMocks
    MemberController memberController;

    private MockMvc mockMvc;//Controller를 테스트하기 위한 객체


    @BeforeEach
    // 테스트 시작 전 호출하게 되는 메서드
    public void init(){
        //mockMvc객체를 초기화해주는 작업
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }

//    @Test
//    @DisplayName("유저 닉네임 변경 테스트(성공)")
//    @WithUserDetails
//    void setNickname() throws Exception{
//        //given
//        //UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
//        //Member member = new Member("user@user.com","asd12345","user1");
//        String updateNickname = "update";
//        //given(userDetails.getMember()).willReturn(member);
//        //given(memberService.updateNickname(updateNickname,userDetails.getMember())).willReturn("수정이 완료되었습니다!");
//
//        //ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body("수정이 완료되었습니다!");
//        ResultActions resultActions = mockMvc.perform(
//                MockMvcRequestBuilders.put("/member/mypage/nickname")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new Gson().toJson(updateNickname))
//
//
//        );
//        resultActions.andExpect(status().isOk());
//
//    }
}