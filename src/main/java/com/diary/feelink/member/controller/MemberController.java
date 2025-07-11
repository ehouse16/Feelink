package com.diary.feelink.member.controller;

import com.diary.feelink.common.ApiResponse;
import com.diary.feelink.member.dto.request.SignUpRequest;
import org.springframework.http.ResponseEntity;

public interface MemberController {
    public ResponseEntity<ApiResponse<Void>> signUp(SignUpRequest signUpRequest);
}
