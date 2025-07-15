package com.diary.feelink.myDiary.controller;

import com.diary.feelink.common.ApiResponse;
import com.diary.feelink.diary.dto.request.DiaryUpdateRequest;
import com.diary.feelink.diary.dto.response.DiaryResponse;
import com.diary.feelink.member.annotation.LoginMember;
import com.diary.feelink.member.entity.Member;
import com.diary.feelink.myDiary.service.MyDiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/my")
public class MyDiaryControllerImpl implements MyDiaryController {

    private final MyDiaryService myDiaryService;

    @PutMapping("/{diaryId}")
    public ResponseEntity<ApiResponse<DiaryResponse>> update(
            @PathVariable Long diaryId,
            @RequestBody @Validated DiaryUpdateRequest request,
            @LoginMember Member member) {
        DiaryResponse diaryResponse = myDiaryService.update(diaryId, request, member);

        return ResponseEntity.ok(new ApiResponse<>(true, "성공적으로 수정되었습니다.", diaryResponse));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DiaryResponse>>> getAllDiary(
            @LoginMember Member member
    ){
        List<DiaryResponse> diaries = myDiaryService.getDiaries(member);

        return ResponseEntity.ok(new ApiResponse<>(true, "성공적으로 일기들을 반환하였습니다", diaries));
    }

    @GetMapping("/{diaryId}")
    public ResponseEntity<ApiResponse<DiaryResponse>> getDiary(
            @PathVariable Long diaryId,
            @LoginMember Member member
    ){
        DiaryResponse response = myDiaryService.getDiary(diaryId, member);

        return ResponseEntity.ok(new ApiResponse<>(true, "성공적으로 일기를 반환하였습니다.", response));
    }

    @DeleteMapping("/{diaryId}")
    public ResponseEntity<ApiResponse<Void>> deleteDiary(
            @PathVariable Long diaryId,
            @LoginMember Member member
    ){
        myDiaryService.delete(diaryId,member);

        return ResponseEntity.ok(new ApiResponse<>(true, "성공적으로 삭제되었습니다.", null));
    }
}
