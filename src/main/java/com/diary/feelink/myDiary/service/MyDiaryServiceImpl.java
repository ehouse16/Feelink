package com.diary.feelink.myDiary.service;

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
public class MyDiaryServiceImpl implements MyDiaryService {

    private final DiaryRepository diaryRepository;

    @Override
    @Transactional
    public DiaryResponse update(Long diaryId, DiaryUpdateRequest request, Member member) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new DomainException(ErrorType.DIARY_NOT_FOUND));

        if(!diary.getMember().getId().equals(member.getId())){
            throw new DomainException(ErrorType.MEMBER_DIARY_NOT_MATCH);
        }

        diary.update(request);

        return DiaryResponse.fromEntity(diary);
    }

    @Override
    public List<DiaryResponse> getDiaries(Member member) {
        return diaryRepository.findAllByMemberId(member.getId()).stream()
                .map(diary -> DiaryResponse.fromEntity(diary))
                .toList();
    }

    @Override
    public DiaryResponse getDiary(Long diaryId, Member member) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new DomainException(ErrorType.DIARY_NOT_FOUND));

        if(!diary.getMember().getId().equals(member.getId())) {
            throw new DomainException(ErrorType.MEMBER_DIARY_NOT_MATCH);
        }

        return DiaryResponse.fromEntity(diary);
    }

    @Override
    public void delete(Long diaryId, Member member) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new DomainException(ErrorType.DIARY_NOT_FOUND));

        if(!diary.getMember().getId().equals(member.getId())){
            throw new DomainException(ErrorType.MEMBER_DIARY_NOT_MATCH);
        }

        diaryRepository.delete(diary);
    }
}
