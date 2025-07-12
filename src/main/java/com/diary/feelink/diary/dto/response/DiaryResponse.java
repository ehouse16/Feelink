package com.diary.feelink.diary.dto.response;

import com.diary.feelink.diary.entity.Diary;
import com.diary.feelink.diary.entity.EmotionType;

public record DiaryResponse(
        String title,
        String content,
        EmotionType emotionType
) {
    public static DiaryResponse fromEntity(Diary diary) {
        return new DiaryResponse(
                diary.getTitle(),
                diary.getContent(),
                diary.getEmotionType()
        );
    }
}
