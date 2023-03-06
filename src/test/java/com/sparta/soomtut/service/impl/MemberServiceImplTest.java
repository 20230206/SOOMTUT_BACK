package com.sparta.soomtut.service.impl;

import com.sparta.soomtut.lecture.entity.Lecture;
import com.sparta.soomtut.lecture.service.impl.LectureServiceImpl;
import com.sparta.soomtut.lectureRequest.entity.LectureRequest;
import com.sparta.soomtut.lectureRequest.repository.LectureRequestRepository;
import com.sparta.soomtut.member.entity.Member;
import com.sparta.soomtut.member.repository.MemberRepository;
import com.sparta.soomtut.member.service.impl.MemberServiceImpl;
import com.sparta.soomtut.review.dto.request.CreateReviewRequestDto;
import com.sparta.soomtut.review.repository.ReviewRepository;
import com.sparta.soomtut.review.service.impl.ReviewServiceImpl;
import com.sparta.soomtut.location.entity.Location;
import com.sparta.soomtut.location.service.impl.LocationServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @Mock
    ReviewRepository reviewRepository;

    @Mock
    LocationServiceImpl locationService;

    @Mock
    MemberRepository memberRepository;
    @InjectMocks
    MemberServiceImpl memberService;

    @Mock
    ReviewServiceImpl reviewService;

    @Mock
    LectureServiceImpl postService;

    @Test
    @DisplayName("유저 닉네임 업데이트(성공)")
    void updateNickname() {

        // Member member = new Member("user@user.com","asd12345","user1");

        // var msg = memberService.updateNickname("new nickname", member);

        // assertThat(member.getNickname()).isEqualTo("new nickname");

    }

    @Test
    @DisplayName("수강 후기 작성(MemberService)")
    void createReview(){
        // Long postId = 1L;
        // Long tutorId = 1L;
        // Member member = new Member("user@user.com","asd12345","user1");
        // Lecture lecture = new Lecture("아무개",1,1);
        // CreateReviewRequestDto createReviewRequestDto = CreateReviewRequestDto.builder().review_content("굿!").star_rating(3f).build();

        // given(reviewService.checkTuitionState(1L,member.getId())).willReturn(true);
        // given(postService.getTutorId(postId)).willReturn(tutorId);
        // 저 부분 바뀌었으니 알아서 바꾸십쇼. ㅎ
//        given(lectureRequestRepository.findByPostId(anyLong())).willReturn(Optional.ofNullable(mock(LectureRequest.class)));

        // String msg = memberService.createReview(postId,createReviewRequestDto,member);
       // verify(reviewRepository,times(1)).save(isA(Review.class));
        // assertThat(msg).isEqualTo("수강후기 작성이 완료되었습니다!");
    }









}