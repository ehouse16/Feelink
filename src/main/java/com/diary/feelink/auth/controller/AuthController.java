package com.diary.feelink.auth.controller;

import com.diary.feelink.common.ApiResponse;
import com.diary.feelink.auth.dto.request.LogInRequest;
import com.diary.feelink.auth.dto.request.SignUpRequest;
import com.diary.feelink.auth.dto.response.LogInResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface AuthController {
    ResponseEntity<ApiResponse<Void>> signUp(SignUpRequest signUpRequest);
    ResponseEntity<ApiResponse<LogInResponse>> logIn(LogInRequest logInRequest, HttpServletRequest httpRequest);
}
