package com.diary.feelink.member.controller;

import com.diary.feelink.common.ApiResponse;
import com.diary.feelink.member.dto.request.LogInRequest;
import com.diary.feelink.member.dto.request.SignUpRequest;
import com.diary.feelink.member.dto.response.LogInResponse;
import com.diary.feelink.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberControllerImpl implements MemberController {
    private final MemberService memberService;
    private final RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        memberService.register(signUpRequest);

        return ResponseEntity.ok(new ApiResponse<>(true, "성공적으로 등록되었습니다!", null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LogInResponse>> logIn(@Valid @RequestBody LogInRequest logInRequest,
                                                            HttpServletRequest httpRequest) {
        LogInResponse response = memberService.logIn(logInRequest);

        String sessionKey = UUID.randomUUID().toString();
        httpRequest.getSession(true).setAttribute("LOGIN_TOKEN", sessionKey);

        redisTemplate.opsForValue().set("session:" + sessionKey, response.nickname(), Duration.ofMinutes(30));

        return ResponseEntity.ok(new ApiResponse<>(true, "성공적으로 로그인하였습니다!", response));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logOut(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(false);

        if(session != null) {
            String sessionToken = (String) session.getAttribute("LOGIN_TOKEN");
            if(sessionToken != null) {
                String sessionKey = "session:" + sessionToken;
                redisTemplate.delete(sessionKey);
            }
            session.invalidate();

        }
        return ResponseEntity.ok(new ApiResponse<>(true, "성공적으로 로그아웃되었습니다.", null));
    }
}
