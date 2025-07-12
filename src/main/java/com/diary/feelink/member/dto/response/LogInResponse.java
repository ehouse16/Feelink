package com.diary.feelink.member.dto.response;

import com.diary.feelink.member.entity.Member;

public record LogInResponse(
        String nickname
) {
    public static LogInResponse fromEntity(Member member) {
        return new LogInResponse(member.getNickname());
    }
}
