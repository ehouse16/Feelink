package com.diary.feelink.diary.entity;

import com.diary.feelink.common.BaseEntity;
import com.diary.feelink.diary.dto.request.DiaryUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(name = "member_id")
    private Long memberId;

    @Builder
    public Diary (String title, String content, EmotionType emotionType, Long memberId) {
        this.title = title;
        this.content = content;
        this.emotionType = emotionType;
        this.memberId = memberId;
    }

    public void update(DiaryUpdateRequest request) {
        this.title = request.title();
        this.content = request.content();
    }
}
