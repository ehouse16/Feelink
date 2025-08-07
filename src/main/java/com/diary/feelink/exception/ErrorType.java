package com.diary.feelink.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    //VALIDATION
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "값을 잘못 입력했습니다."),

    //MEMBER
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
    DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST, "이미 존재하는 닉네임입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하는 회원을 찾을 수 없습니다"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "올바르지 않은 비밀번호입니다."),
    LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
    SESSION_EXPIRED(HttpStatus.UNAUTHORIZED, "세션이 만료되었거나 잘못되었습니다."),
    SESSION_NOT_FOUND(HttpStatus.UNAUTHORIZED, "세션이 존재하지 않습니다"),

    //DIARY
    DIARY_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하는 일기를 찾을 수 없습니다."),
    MEMBER_DIARY_NOT_MATCH(HttpStatus.BAD_REQUEST, "자기가 쓴 일기만 수정,삭제 할 수 있습니다."),

    //AI
    CANNOT_PARSE(HttpStatus.BAD_REQUEST, "감정을 변환할 수 없습니다."),

    //COMMENT
    PARENT_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "부모 댓글을 찾을 수 없습니다." ),
    COMMENT_DEPTH_EXCEEDED(HttpStatus.BAD_REQUEST, "댓글은 1단계까지만 달 수 있습니다." );

    private final HttpStatus status;
    private final String message;
}
