package com.diary.feelink.diary.entity;

import com.diary.feelink.comment.entity.Comment;
import com.diary.feelink.common.BaseEntity;
import com.diary.feelink.diary.dto.request.DiaryUpdateRequest;
import com.diary.feelink.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@Table(name = "diaries")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary extends BaseEntity {
    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "emotion_type")
    @Enumerated(EnumType.STRING)
    private EmotionType emotionType;

    @Column(name = "confidence")
    private Double confidence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "diary")
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Diary (String title, String content, EmotionType emotionType, Double confidence, Member member) {
        this.title = title;
        this.content = content;
        this.emotionType = emotionType;
        this.confidence = confidence;
        this.member = member;
    }

    public void update(DiaryUpdateRequest request) {
        this.title = request.title();
        this.content = request.content();
    }
}
