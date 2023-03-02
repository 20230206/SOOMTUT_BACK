package com.sparta.soomtut.util.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    // auth responses
    LOGIN_OK("로그인에 성공하였습니다", 200),
    MESSGE_OK("메세지가 전달 되었습니다", 200),
    REGISTER_CHECK_OK("회원가입-유효성 검사에 성공하였습니다", 200),
    LOGOUT_OK("로그아웃에 성공하였습니다", 200),
    TOKEN_CHECK_OK("토큰 검증에 성공하였습니다", 200),
    REFRESH_OK("리프레쉬 토큰 발급에 성공하였습니다", 200),
    OAUTH_LOGIN_OK("소셜 로그인에 성공하였습니다", 200),
    
    MEMBER_GETMYINFO_OK("내 정보 조회에 성공했습니다", 200),
    MEMBER_SUSPEND_OK("회원 탈퇴 요청에 성공했습니다", 200),
    MEMBER_RECOVER_OK("회원 탈퇴 취소 요청에 성공했습니다", 200),
    MEMBER_GETINFO_OK("회원 정보 조회에 성공했습니다", 200),
    MEMBER_UPDATEINFO_OK("내 정보 수정에 성공했습니다.", 200),
    
    LECTURE_CREATE_OK("수업 등록 성공", 203),
    LECTURE_UPDATE_OK("수업 수정 성공", 200),
    LECTURE_DELETE_OK("수업 삭제 성공", 200),
    LECTURE_GETLECTURE_OK("수업 조회 성공", 200),
    LECTURE_GETLECTURES_OK("수업 전체 조회 성공", 200),
    LECTURE_CHECK_OK("내 수업 확인 성공", 200),
    LECTURE_GETDONELECUTES_OK("완료된 수업 목록 조회", 200),
    
    LECTURE_UPDATEBOOKMARK_OK("북마크 추가 성공", 200),
    LECTURE_GETBOOKMARKEDLECTURES_OK("북마크된 전체 수업 조회", 200),
    
    LECTUREREQUEST_CREATE_OK("수업 신청 성공", 200),
    LECTUREREQUEST_ACCEPT_OK("수업 확정 성공", 200),
    LECTUREREQUEST_COMPLETE_OK("수업 완료 성공", 200),
    LECTUREREQUEST_GETREQUESTS_OK("수업 목록 조회 성공", 200),
    
    REVIEW_CREATE_OK("리뷰 생성 성공", 200),
    REVIEW_GET_OK("리뷰 조회 성공", 200),
    REVIEW_DELETE_OK("리뷰 삭제 성공", 200),
    REVIEW_GETBYLECTURE_OK("강의의 리뷰 조회 성공", 200),
    REVIEW_GETBYMEMBER_OK("해당 유저의 리뷰 조회 성공", 200),
    
    IMG_PROFILE_OK("프로필 업로드 성공", 200),

    IMG_POSTIMG_OK("강의글 이미지 업로드 성공", 200);

    private final String message;
    private final int status;
}