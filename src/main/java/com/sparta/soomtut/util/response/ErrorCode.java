package com.sparta.soomtut.util.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    INVALID_AUTH_TOKEN(401, "권한 정보가 없는 토큰입니다"),
    INVALID_TOKEN(401, "토큰이 유효하지 않습니다."),
    AUTHORIZATION(401, "작성자만 수정/삭제할 수 있습니다."),
    LOGIN_FAILED(401, "로그인에 실패했습니다."),
    DUPLICATED_EMAIL(400, "중복된 email 입니다"),
    DUPLICATED_NICKNAME(400,"중복된 닉네임 입니다"),
    NOT_FOUND_USER(400, "회원을 찾을 수 없습니다."),
    NOT_FOUND_CHATROOM(400,"채팅방을 찾을 수 없습니다."),
    INVALID_PASSWORD(500,"비밀번호가 틀렸습니다."),
    SECESSION_USER(500, "탈퇴한 회원입니다."),
    NOT_FOUND_POST(500, "게시글이 존재하지 않습니다."),
    NOT_FOUND_REQUEST(500, "신청한 수강목록이 없습니다."),
    NOT_FOUND_LECTURE_REQUEST(500, "해당 수업 요청을 찾을 수 없습니다."),
    NOT_FOUND_LOCATION(500,"위치 정보를 찾을 수 없습니다."),
    EMPTY_FILE(500, "파일이 없습니다."),
    NOT_IMAGE_FILE(500, "이미지 파일이 아닙니다."),
    NOT_FOUND_CLASS(500, "해당 수업을 찾을 수 없습니다."),
    NOT_FOUND_REVIEW(500, "해당 리뷰를 찾을 수 없습니다."),
    NOT_FOUND_CATEGORY(500, "해당 카테고리를 찾을 수 없습니다."),
    NOT_FOUND_FAVPOST(500,"해당 즐겨찾기를 찾을 수 없습니다"),
    NOT_FOUND_LOG(500, "해당 로그를 찾을 수 없습니다"),
    NOT_FIND_REQUEST(500,"요청내용을 찾을 수 없습니다."),
    DUPLICATED_CHATTING(500,"이미 채팅방이 존재합니다."),
    ALREADY_REQUEST_SELLER(500,"신청 가능한 상태가 아닙니다."),
    ALREADY_HAVA_REVIEW(500,"이미 작성된 후기가 존재합니다."),
    NOT_PROGRESS_CLASS(500, "아직 수강이 완료되지 않았습니다!"),
    CATEGORY_OUTOFINDEX(500, "인덱스 길이를 초과하였습니다."),
    REVIEW_NOT_FOUND(500, "리뷰를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(500, "서버 오류"),
    MISMATCH_CREATOR(401, "수정 권한이 없습니다."),
    DELETED_REVIEW(400, "삭제된 리뷰 입니다.");

    //    ALREADY_SELLER_MANAGEMENT_STATUS_DROP("판매자 권한 회수 상태로 처리가 불가능합니다."),
//    ALREADY_SELLER_MANAGEMENT_STATUS_COMPLETE("판매자 권한 승인 상태로 처리가 불가능합니다."),
//    ALREADY_SELLER_MANAGEMENT_STATUS_REJECT("판매자 승인 취소 상태로 처리가 불가능합니다. 다시 권한 신청해주세요"),
//    ALREADY_SELLER_MANAGEMENT_STATUS_WAIT("판매자 신청 대기 상태입니다."),
//    NOT_SELLER_MANAGEMENT_STATUS_WAIT("판매자 대기 상태가 아니므로 처리 불가능합니다."),

//    NOT_READABLE_JSON("올바르지 않은 JSON 형식입니다."),
//    NOT_SUPPORTED_HTTP_MEDIA_TYPE("지원하지 않는 Content-Type 입니다."),
//    NOT_ACCEPTABLE_HTTP_MEDIA_TYPE("지원하지 않는 Accept 입니다."),

//    NOT_EXIST_CATEGORY("카테고리가 존재하지 않습니다."),
//    DUPLICATED_CATEGORY("중복된 카테고리 입니다."),
//    NOT_CHILD_CATEGORY("현재 카테고리에 생성할 수 없습니다."),
//    NOT_EMPTY_CATEGORY("상품이 등록된 카테고리는 삭제할 수 없습니다."),
    private final int status;
    private final String message;

}
