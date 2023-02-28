package com.sparta.soomtut.member.service.impl;

import com.sparta.soomtut.lecture.entity.Lecture;
import com.sparta.soomtut.lecture.service.LectureService;
import com.sparta.soomtut.member.dto.response.MemberInfoResponse;
import com.sparta.soomtut.member.entity.Member;
import com.sparta.soomtut.member.entity.enums.MemberState;
import com.sparta.soomtut.member.repository.MemberRepository;
import com.sparta.soomtut.member.service.MemberService;
import com.sparta.soomtut.review.dto.request.CreateReviewRequestDto;
import com.sparta.soomtut.review.entity.Review;
import com.sparta.soomtut.review.service.ReviewService;
import com.sparta.soomtut.util.dto.request.PageRequestDto;
import com.sparta.soomtut.util.response.ErrorCode;
import com.sparta.soomtut.location.service.LocationService;
import com.sparta.soomtut.lectureRequest.entity.LectureRequest;
import com.sparta.soomtut.lectureRequest.repository.LectureRequestRepository;
import com.sparta.soomtut.admin.service.DeleteReviewRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;

    private final LectureService lectureService;
    private final ReviewService reviewService;
    private final LocationService locationService;
    private final DeleteReviewRequestService deleteReviewRequestService;
    private final LectureRequestRepository lectureRequestRepository;

    @Override
    public String updateNickname(String nickname, Member member) {

        member.updateNickName(nickname);

        return "수정이 완료되었습니다!";
    }

    @Override
    public String getNickname(Member member) {

        return member.getNickname();

    }
    @Override
    public String getLocation(Member member) {

        return locationService.getLocation(member).getAddress();

    }
    @Override
    public LocalDate getSignupDate(Member member) {

        return member.getCreatedAt();

    }
    @Override
    public int getLevel(Member member) {
        return member.getLevel();
    }
    @Override
    public String getImage(Member member) {
        return member.getImage();
    }
    
    @Override
    @Transactional
    public String createReview(Long lectureId, CreateReviewRequestDto reviewRequestDto, Member member) {
        // lectureId 가 lectureId 가 아닌 Lecture 를 객체로 가지고 있게 되면서 수정해야 할 부분들이 많이 생겨났습니다.
        // 이 로직도 그 중 하나인데, 제가 수정은 했지만 로직이 맞는 지 확인은 안 했습니다. 확인해 주세요.
        if(!reviewService.checkTuitionState(lectureId,member.getId())){
            //수강신청한 강좌의 상태가 In_Progress상태
            throw new IllegalArgumentException(ErrorCode.NOT_PROGRESS_CLASS.getMessage());
        }
        Long tutorId = lectureService.getTutorId(lectureId);
        reviewService.saveReview(tutorId,reviewRequestDto,member.getId());

        Lecture lecture = lectureService.getLectureById(lectureId);

        LectureRequest lectureRequest = lectureRequestRepository.findByLecture(lecture).orElseThrow(
                () -> new IllegalArgumentException("Error")
        );

        lectureRequest.ChangTuitionReview(lecture);

        return "수강후기 작성이 완료되었습니다!";

    }

    @Override
    @Transactional
    public MemberInfoResponse suspendAccount(Long memberId) {
        Member member = getMemberById(memberId);
    
        member.changeState(MemberState.SUSPEND);
        var location = locationService.findMemberLocation(memberId);

        return MemberInfoResponse.toDto(member, location);
    }






    // repository 지원 함수

    @Override
    @Transactional
    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                ()->new IllegalArgumentException(ErrorCode.NOT_FOUND_USER.getMessage())
               );
    }

    @Override
    @Transactional
    public Member saveMember(Member member){
        return memberRepository.save(member);
    }

    @Override
    public Member getMemberByNickname(String nickname) {
        return memberRepository.findByNickname(nickname).orElseThrow(()->new IllegalArgumentException(ErrorCode.NOT_FOUND_USER.getMessage()));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsMemberByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsMemberByNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);    
    }

    @Override
    @Transactional(readOnly = true)
    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
            () -> new IllegalArgumentException("등록된 사용자가 없습니다!")
        );

    }

    @Override
    @Transactional(readOnly = true)
    public Page<Review> getReview(PageRequestDto pageRequestDto, Member member) {
        return reviewService.getReview(pageRequestDto, member.getId());
    }

    @Override
    public String deleteReviewRequest(Long reviewId) {
        return deleteReviewRequestService.deleteReviewRequest(reviewId);
    }

    @Override
    public MemberInfoResponse getMemberInfo(Member member) {
        
        return MemberInfoResponse.toDto(member, locationService.findMemberLocation(member.getId()));
    }

    @Override
    @Transactional
    public Optional<Member> findByProviderAndOauthEmail(String provider, String email) {
        return memberRepository.findByProviderAndOauthEmail(provider, email);
    }

    @Override
    public MemberInfoResponse getMemberInfoResponseDto(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(()->new IllegalArgumentException(ErrorCode.NOT_FOUND_USER.getMessage()));
        MemberInfoResponse memberInfoResponseDto = MemberInfoResponse.toDto(member,locationService.findMemberLocation(memberId));
        return memberInfoResponseDto;
    }
}
