package com.diary.feelink.diary.service;

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

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;

    @Override
    @Transactional
    public DiaryResponse register(DiaryRegisterRequest diaryRegisterRequest, Member member) {
        Diary diary = Diary.builder()
                .title(diaryRegisterRequest.title())
                .content(diaryRegisterRequest.content())
                //ai 붙이고 나서 감정 추가하기
                .emotionType(null)
                .memberId(member.getId())
                .build();

        diaryRepository.save(diary);

        return DiaryResponse.fromEntity(diary);
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

        return DiaryResponse.fromEntity(diary);
    }
}
