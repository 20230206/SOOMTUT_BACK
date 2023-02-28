package com.sparta.soomtut.service.impl;

import com.sparta.soomtut.lecture.entity.Lecture;
import com.sparta.soomtut.lecture.service.impl.LectureServiceImpl;
import com.sparta.soomtut.lectureRequest.entity.LectureRequest;
import com.sparta.soomtut.lectureRequest.repository.LectureRequestRepository;
import com.sparta.soomtut.member.entity.Member;
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
    LectureRequestRepository tuitionRequestRepository;

    @InjectMocks
    MemberServiceImpl memberService;

    @Mock
    ReviewServiceImpl reviewService;

    @Mock
    LectureServiceImpl postService;

    @Test
    @DisplayName("유저 닉네임 업데이트(성공)")
    void updateNickname() {

        Member member = new Member("user@user.com","asd12345","user1");

        String msg = memberService.updateNickname("new nickname",member);

        assertThat(msg).isEqualTo("수정이 완료되었습니다!");
        assertThat(member.getNickname()).isEqualTo("new nickname");

    }

    @Test
    @DisplayName("닉네임 가져오기")
    void getNickname(){
        Member member = new Member("user@user.com","asd12345","user1");
        //given(memberService.findMemberById(member.getId())).willReturn(member);
        String nickName = memberService.getNickname(member);
        assertThat(member.getNickname()).isEqualTo(nickName);

    }

    @Test
    @DisplayName("위치정보 가져오기")
    void getLocation(){
        Member member = new Member("user@user.com","asd12345","user1");
        Location location = new Location(member,"서울",3f,3f);
        given(locationService.getLocation(member)).willReturn(location);
        String address = memberService.getLocation(member);
        assertThat(address).isEqualTo("서울");

    }

    @Test
    @DisplayName("가입일자 가져오기")
    void getSignupDate(){
        Member member = new Member("user@user.com","asd12345","user1");
        LocalDate signupDate = memberService.getSignupDate(member);

        assertThat(signupDate).isEqualTo(member.getCreatedAt());

    }

    @Test
    @DisplayName("레벨 가져오기")
    void getLevel(){
        Member member = new Member("user@user.com","asd12345","user1");
        int level = memberService.getLevel(member);
        assertThat(level).isEqualTo(0);

    }

    @Test
    @DisplayName("이미지 가져오기")
    void getImage(){
        Member member = new Member("user@user.com","asd12345","user1");
        String image = memberService.getImage(member);
        assertThat(image).isEqualTo(member.getImage());


    }

    @Test
    @DisplayName("수강 후기 작성(MemberService)")
    void createReview(){
        Long postId = 1L;
        Long tutorId = 1L;
        Member member = new Member("user@user.com","asd12345","user1");
        Lecture lecture = new Lecture("아무개",1,1);
        CreateReviewRequestDto createReviewRequestDto = CreateReviewRequestDto.builder().review_content("굿!").star_rating(3f).build();

        given(reviewService.checkTuitionState(1L,member.getId())).willReturn(true);
        given(postService.getTutorId(postId)).willReturn(tutorId);
        given(tuitionRequestRepository.findByPostId(anyLong())).willReturn(Optional.ofNullable(mock(LectureRequest.class)));

        String msg = memberService.createReview(postId,createReviewRequestDto,member);
       // verify(reviewRepository,times(1)).save(isA(Review.class));
        assertThat(msg).isEqualTo("수강후기 작성이 완료되었습니다!");
    }









}