package com.diary.feelink.diary.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DiaryUpdateRequest(
        @NotBlank(message = "제목은 필수 값입니다.")
        @Size(min = 2, max = 30, message = "제목은 최소 2자리 이상 최대 30자리 이하이어야 합니다.")
        String title,
        @NotBlank(message = "내용은 필수 값입니다.")
        @Size(min = 10, max = 1000, message = "내용은 최소 10자리 이상, 최대 1000자리 이하이어야 합니다.")
        String content
) {}
