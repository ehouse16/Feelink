package com.diary.feelink.comment.service;

import com.diary.feelink.comment.dto.request.CommentRegisterRequest;
import com.diary.feelink.comment.dto.response.CommentResponse;
import com.diary.feelink.member.entity.Member;

public interface CommentService {
    CommentResponse register(CommentRegisterRequest request, Member member);
}
