package com.diary.feelink.auth.controller;

import com.diary.feelink.common.ApiResponse;
import com.diary.feelink.auth.dto.request.LogInRequest;
import com.diary.feelink.auth.dto.request.SignUpRequest;
import com.diary.feelink.auth.dto.response.LogInResponse;
import com.diary.feelink.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;
    private final RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        authService.register(signUpRequest);

        return ResponseEntity.ok(new ApiResponse<>(true, "성공적으로 등록되었습니다!", null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LogInResponse>> logIn(@Valid @RequestBody LogInRequest logInRequest,
                                                            HttpServletRequest httpRequest) {
        LogInResponse response = authService.logIn(logInRequest);

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

    @GetMapping("/check-email/{email}")
    public ResponseEntity<ApiResponse<Boolean>> checkEmail(@PathVariable String email) {
        return ResponseEntity.ok(new ApiResponse<>(true, "중복 검사 완료", !authService.isExistEmail(email)));
    }

    @GetMapping("check-nickname/{nickname}")
    public ResponseEntity<ApiResponse<Boolean>> checkNickname(@PathVariable String nickname) {
        return ResponseEntity.ok(new ApiResponse<>(true, "중복 검사 완료", !authService.isExistNickName(nickname)));
    }
}
