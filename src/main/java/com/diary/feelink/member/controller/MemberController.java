package com.diary.feelink.member.controller;

import com.diary.feelink.common.ApiResponse;
import com.diary.feelink.member.dto.request.LogInRequest;
import com.diary.feelink.member.dto.request.SignUpRequest;
import com.diary.feelink.member.dto.response.LogInResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface MemberController {
    ResponseEntity<ApiResponse<Void>> signUp(SignUpRequest signUpRequest);
    ResponseEntity<ApiResponse<LogInResponse>> logIn(LogInRequest logInRequest, HttpServletRequest httpRequest);
}
