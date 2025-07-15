package com.diary.feelink.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        String email,
        @Size(min = 6, max = 12, message = "비밀번호는 6자리 이상 12자리 이하여야 합니다.")
        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password,
        @Size(min = 2, max = 8, message = "닉네임은 2자리 이상 8자리 이하여야 합니다.")
        @NotBlank(message = "닉네임을 입력해주세요")
        String nickname
) {
}
