package com.diary.feelink.ai.dto.response;

import com.diary.feelink.diary.entity.EmotionType;

public record EmotionResult(
        EmotionType emotionType,
        double confidence
) {
}
