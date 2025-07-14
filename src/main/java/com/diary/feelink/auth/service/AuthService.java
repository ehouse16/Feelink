package com.diary.feelink.auth.service;

import com.diary.feelink.auth.dto.request.LogInRequest;
import com.diary.feelink.auth.dto.request.SignUpRequest;
import com.diary.feelink.auth.dto.response.LogInResponse;
import jakarta.validation.Valid;

public interface AuthService {
    void register(SignUpRequest signUpRequest);
    LogInResponse logIn(@Valid LogInRequest logInRequest);
    boolean isExistEmail(String email);
    boolean isExistNickName(String nickname);
}
