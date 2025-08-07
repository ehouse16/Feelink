package com.diary.feelink.comment.controller;

import com.diary.feelink.comment.dto.request.CommentRegisterRequest;
import com.diary.feelink.comment.dto.response.CommentResponse;
import com.diary.feelink.comment.service.CommentService;
import com.diary.feelink.common.ApiResponse;
import com.diary.feelink.member.annotation.LoginMember;
import com.diary.feelink.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentControllerImpl implements CommentController{

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponse>> registerComment(
            @RequestBody @Validated CommentRegisterRequest request,
            @LoginMember Member member
    ){

        CommentResponse response = commentService.register(request, member);

        return ResponseEntity.ok(new ApiResponse<>(true, "성공적으로 댓글이 등록되었습니다", response));
    }
}
