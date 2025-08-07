package com.diary.feelink.comment.dto.response;

public record CommentResponse(
        Long id,
        Long parentId,
        String content,
        String nickname
) {
}
