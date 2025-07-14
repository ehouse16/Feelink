package com.diary.feelink.myDiary.service;

import com.diary.feelink.diary.dto.request.DiaryUpdateRequest;
import com.diary.feelink.diary.dto.response.DiaryResponse;
import com.diary.feelink.member.entity.Member;

import java.util.List;

public interface MyDiaryService {
    DiaryResponse update(Long diaryId, DiaryUpdateRequest request, Member member);
    List<DiaryResponse> getDiaries(Member member);
    DiaryResponse getDiary(Long diaryId, Member member);
    void delete(Long diaryId, Member member);
}
