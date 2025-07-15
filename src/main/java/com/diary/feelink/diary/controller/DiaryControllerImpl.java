package com.diary.feelink.diary.controller;

import com.diary.feelink.common.ApiResponse;
import com.diary.feelink.diary.dto.request.DiaryRegisterRequest;
import com.diary.feelink.diary.dto.request.DiaryUpdateRequest;
import com.diary.feelink.diary.dto.response.DiaryResponse;
import com.diary.feelink.diary.service.DiaryService;
import com.diary.feelink.member.annotation.LoginMember;
import com.diary.feelink.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diaries")
public class DiaryControllerImpl implements DiaryController {

    private final DiaryService diaryService;

    @PostMapping
    public ResponseEntity<ApiResponse<DiaryResponse>> register(
            @RequestBody @Validated DiaryRegisterRequest diaryRegisterRequest,
            @LoginMember Member member) {
        DiaryResponse diaryResponse = diaryService.register(diaryRegisterRequest, member);

        return ResponseEntity.ok(new ApiResponse<>(true, "성공적으로 등록되었습니다.", diaryResponse));
    }

    //전체 목록 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<DiaryResponse>>> getDiaries(
    ){
        return ResponseEntity.ok(new ApiResponse<>(true, "성공적으로 목록을 불러왔습니다.",
                diaryService.getDiaries()));
    }

    //상세 보기
    @GetMapping("/{diaryId}")
    public ResponseEntity<ApiResponse<DiaryResponse>> getDiary(
            @PathVariable Long diaryId
    ){
        DiaryResponse response = diaryService.getDiary(diaryId);

        return ResponseEntity.ok(new ApiResponse<>(true, "성공적으로 일기를 반환하였습니다.", response));
    }
    //검색
}
