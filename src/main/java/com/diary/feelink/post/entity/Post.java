package com.diary.feelink.post.entity;

import com.diary.feelink.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {
    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "emotion_type")
    @Enumerated(EnumType.STRING)
    private EmotionType emotionType;

    @Column(name = "member_id")
    private Long memberId;
}
