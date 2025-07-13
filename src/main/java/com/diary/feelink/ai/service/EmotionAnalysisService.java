package com.diary.feelink.ai.service;

import com.diary.feelink.ai.dto.response.EmotionResult;

public interface EmotionAnalysisService {
    EmotionResult analyzeEmotion(String diaryContent);
}
