package com.diary.feelink.member.controller;

import com.diary.feelink.common.ApiResponse;
import com.diary.feelink.member.dto.request.SignUpRequest;
import com.diary.feelink.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberControllerImpl implements MemberController {
    private final MemberService memberService;

    @PostMapping("/signUp")
    public ResponseEntity<ApiResponse<Void>> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        memberService.register(signUpRequest);

        return ResponseEntity.ok(new ApiResponse<>(true, "성공적으로 등록되었습니다!", null));
    }
}
