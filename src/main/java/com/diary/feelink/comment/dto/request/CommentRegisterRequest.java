package com.diary.feelink.comment.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentRegisterRequest(
        @NotNull(message = "일기 ID는 필수 값입니다.")
        Long diaryId,
        @Nullable
        Long parentId,
        @NotBlank(message = "댓글 내용은 필수 값입니다.")
        String content
) {
}
