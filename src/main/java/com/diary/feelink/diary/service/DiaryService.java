package com.diary.feelink.diary.service;

import com.diary.feelink.diary.dto.request.DiaryRegisterRequest;
import com.diary.feelink.diary.dto.request.DiaryUpdateRequest;
import com.diary.feelink.diary.dto.response.DiaryResponse;
import com.diary.feelink.member.entity.Member;

import java.util.List;

public interface DiaryService {
    DiaryResponse register(DiaryRegisterRequest diaryRegisterRequest, Member member);
    List<DiaryResponse> getDiaries();
    DiaryResponse getDiary(Long diaryId);
}
