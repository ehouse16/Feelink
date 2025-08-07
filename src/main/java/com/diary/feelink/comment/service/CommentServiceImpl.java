package com.diary.feelink.comment.service;

import com.diary.feelink.comment.dto.request.CommentRegisterRequest;
import com.diary.feelink.comment.dto.response.CommentResponse;
import com.diary.feelink.comment.entity.Comment;
import com.diary.feelink.comment.repository.CommentRepository;
import com.diary.feelink.diary.entity.Diary;
import com.diary.feelink.diary.repository.DiaryRepository;
import com.diary.feelink.exception.DomainException;
import com.diary.feelink.exception.ErrorType;
import com.diary.feelink.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final DiaryRepository diaryRepository;

    @Override
    public CommentResponse register(CommentRegisterRequest request, Member member) {
        Diary diary = diaryRepository.findById(request.diaryId())
                .orElseThrow(() -> new DomainException(ErrorType.DIARY_NOT_FOUND));

        Comment parent = null;
        if(request.parentId() != null) {
            parent = commentRepository.findById(request.parentId())
                    .orElseThrow(() -> new DomainException(ErrorType.PARENT_COMMENT_NOT_FOUND));

            if(!parent.canAddReply())
                throw new DomainException(ErrorType.COMMENT_DEPTH_EXCEEDED);
        }

        Comment comment = Comment.builder()
                .content(request.content())
                .diary(diary)
                .member(member)
                .parent(parent)
                .build();

        commentRepository.save(comment);

        CommentResponse response = new CommentResponse(
                comment.getId(),
                comment.getParent() != null ? comment.getParent().getId() : null, // NPE 방지
                comment.getContent(),
                comment.getMember().getNickname()
        );

        return response;
    }
}
