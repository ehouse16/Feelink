package com.diary.feelink.member.service;

import com.diary.feelink.member.dto.request.LogInRequest;
import com.diary.feelink.member.dto.request.SignUpRequest;
import com.diary.feelink.member.dto.response.LogInResponse;
import jakarta.validation.Valid;

public interface MemberService {
    void register(SignUpRequest signUpRequest);
    LogInResponse logIn(@Valid LogInRequest logInRequest);
}
