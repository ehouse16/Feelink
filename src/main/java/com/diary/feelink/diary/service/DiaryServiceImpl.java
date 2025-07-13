package com.diary.feelink.diary.service;

import com.diary.feelink.ai.dto.response.EmotionResult;
import com.diary.feelink.ai.service.EmotionAnalysisService;
import com.diary.feelink.diary.dto.request.DiaryRegisterRequest;
import com.diary.feelink.diary.dto.request.DiaryUpdateRequest;
import com.diary.feelink.diary.dto.response.DiaryResponse;
import com.diary.feelink.diary.entity.Diary;
import com.diary.feelink.diary.repository.DiaryRepository;
import com.diary.feelink.exception.DomainException;
import com.diary.feelink.exception.ErrorType;
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
                .memberId(member.getId())
                .build();

        diaryRepository.save(diary);

        return DiaryResponse.fromEntity(diary, member.getNickname());
    }

    @Override
    @Transactional
    public DiaryResponse update(Long diaryId, DiaryUpdateRequest request, Member member) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new DomainException(ErrorType.DIARY_NOT_FOUND));

        if(!diary.getMemberId().equals(member.getId())){
            throw new DomainException(ErrorType.MEMBER_DIARY_NOT_MATCH);
        }

        diary.update(request);

        return DiaryResponse.fromEntity(diary, member.getNickname());
    }

    @Override
    public List<DiaryResponse> getDiaries(Member member) {
        return diaryRepository.findAllByMemberId(member.getId()).stream()
                .map(diary -> DiaryResponse.fromEntity(diary, member.getNickname()))
                .toList();
    }

    @Override
    public DiaryResponse getDiary(Long diaryId, Member member) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new DomainException(ErrorType.DIARY_NOT_FOUND));

        if(!diary.getMemberId().equals(member.getId())) {
            throw new DomainException(ErrorType.MEMBER_DIARY_NOT_MATCH);
        }

        return DiaryResponse.fromEntity(diary, member.getNickname());
    }

    @Override
    public void delete(Long diaryId, Member member) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new DomainException(ErrorType.DIARY_NOT_FOUND));

        if(!diary.getMemberId().equals(member.getId())){
            throw new DomainException(ErrorType.MEMBER_DIARY_NOT_MATCH);
        }

        diaryRepository.delete(diary);
    }
}
