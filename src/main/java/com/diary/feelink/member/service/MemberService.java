package com.diary.feelink.member.service;

import com.diary.feelink.member.dto.request.SignUpRequest;

public interface MemberService {
    void register(SignUpRequest signUpRequest);
}
