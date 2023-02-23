package com.sparta.soomtut.service.impl;

import com.sparta.soomtut.dto.request.CreateReviewRequestDto;
import com.sparta.soomtut.dto.request.PageRequestDto;
import com.sparta.soomtut.dto.response.MemberInfoResponse;
import com.sparta.soomtut.entity.Member;
import com.sparta.soomtut.entity.Review;
import com.sparta.soomtut.entity.TuitionRequest;
import com.sparta.soomtut.repository.MemberRepository;
import com.sparta.soomtut.repository.TuitionRequestRepository;
import com.sparta.soomtut.service.interfaces.MemberService;
import com.sparta.soomtut.service.interfaces.PostService;
import com.sparta.soomtut.service.interfaces.ReviewService;
import com.sparta.soomtut.util.response.ErrorCode;
import com.sparta.soomtut.service.interfaces.LocationService;
import com.sparta.soomtut.service.interfaces.DeleteReviewRequestService;
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

    private final PostService postService;
    private final ReviewService reviewService;
    private final LocationService locationService;
    private final DeleteReviewRequestService deleteReviewRequestService;
    private final TuitionRequestRepository tuitionRequestRepository;

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
    public String createReview(Long postId, CreateReviewRequestDto reviewRequestDto, Member member) {
            
        if(!reviewService.checkTuitionState(postId,member.getId())){
            //수강신청한 강좌의 상태가 In_Progress상태
            throw new IllegalArgumentException(ErrorCode.NOT_PROGRESS_CLASS.getMessage());
        }
        Long tutorId = postService.getTutorId(postId);
        reviewService.saveReview(tutorId,reviewRequestDto,member.getId());

        TuitionRequest tuitionRequest = tuitionRequestRepository.findByPostId(postId).orElseThrow(
                () -> new IllegalArgumentException("Error")
        );
        tuitionRequest.ChangTuitionReview(postId);

        return "수강후기 작성이 완료되었습니다!";

    }

    @Override
    @Transactional
    public String deleteAccount(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException(ErrorCode.NOT_FOUND_USER.getMessage())
        );
        member.changeState(false);
        return "계정이 비활성화 되었습니다.";
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
}
