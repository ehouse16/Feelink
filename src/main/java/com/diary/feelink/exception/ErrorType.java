package com.diary.feelink.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    //VALIDATION
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "값을 잘못 입력했습니다.");

    private final HttpStatus status;
    private final String message;
}
