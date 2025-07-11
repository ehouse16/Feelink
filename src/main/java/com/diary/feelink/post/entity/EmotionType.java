package com.diary.feelink.post.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EmotionType {
    JOY("행복"),
    SADNESS("슬픔"),
    ANGER("분노"),
    ANXIETY("불안"),
    SURPRISE("놀람"),
    DISGUST("혐오"),
    NEUTRAL("무감정");

    private final String emotionName;

    EmotionType(String emotionName) {
        this.emotionName = emotionName;
    }

    @JsonValue
    public String getEmotionName() {
        return emotionName;
    }
}
