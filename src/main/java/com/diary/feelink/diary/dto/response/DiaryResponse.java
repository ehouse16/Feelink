package com.diary.feelink.diary.dto.response;

import com.diary.feelink.diary.entity.Diary;
import com.diary.feelink.diary.entity.EmotionType;

import java.time.LocalDateTime;

public record DiaryResponse(
        Long id,
        String title,
        String content,
        EmotionType emotionType,
        String nickName,
        LocalDateTime createdAt
) {
    public static DiaryResponse fromEntity(Diary diary) {
        return new DiaryResponse(
                diary.getId(),
                diary.getTitle(),
                diary.getContent(),
                diary.getEmotionType(),
                diary.getMember().getNickname(),
                diary.getCreatedAt()
        );
    }
}
