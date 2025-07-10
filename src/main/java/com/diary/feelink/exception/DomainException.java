package com.diary.feelink.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DomainException extends RuntimeException {
    private final String message;
    private final HttpStatus status;

    public DomainException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public ExceptionResponse toResponse(){
        return new ExceptionResponse(message);
    }
}
