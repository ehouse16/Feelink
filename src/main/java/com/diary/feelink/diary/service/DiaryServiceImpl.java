package com.diary.feelink.diary.service;

import com.diary.feelink.ai.dto.response.EmotionResult;
import com.diary.feelink.ai.service.EmotionAnalysisService;
import com.diary.feelink.diary.dto.request.DiaryRegisterRequest;
import com.diary.feelink.diary.dto.response.DiaryResponse;
import com.diary.feelink.diary.entity.Diary;
import com.diary.feelink.diary.repository.DiaryRepository;
import com.diary.feelink.member.entity.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;
    private final EmotionAnalysisService emotionAnalysisService;

    @Override
    @Transactional
    public DiaryResponse register(DiaryRegisterRequest diaryRegisterRequest, Member member) {

        EmotionResult result = emotionAnalysisService.analyzeEmotion(diaryRegisterRequest.content());

        Diary diary = Diary.builder()
                .title(diaryRegisterRequest.title())
                .content(diaryRegisterRequest.content())
                .emotionType(result.emotionType())
                .confidence(result.confidence())
                .member(member)
                .build();

        diaryRepository.save(diary);

        return DiaryResponse.fromEntity(diary);
    }

    @Override
    public List<DiaryResponse> getDiaries() {
        return diaryRepository.findAll().stream()
                .map(DiaryResponse::fromEntity)
                .toList();
    }

    @Override
    public DiaryResponse getDiary(Long diaryId) {
        return DiaryResponse.fromEntity(diaryRepository.getById(diaryId));
    }
}
