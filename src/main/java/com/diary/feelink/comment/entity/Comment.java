package com.diary.feelink.comment.entity;

import com.diary.feelink.common.BaseEntity;
import com.diary.feelink.diary.entity.Diary;
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
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {
    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent")
    private List<Comment> children = new ArrayList<>();

    @Builder
    public Comment(String content, Diary diary, Member member, Comment parent) {
        this.content = content;
        this.diary = diary;
        this.member = member;
        this.parent = parent;
    }

    //최상위 댓글인지 확인
    public boolean isParentComment(){
        return this.parent == null;
    }

    //대댓글인지 확인
    public boolean isChildComment(){
        return this.parent != null;
    }

    //대댓글 깊이 제한(최대 1단계)
    public boolean canAddReply(){
        return this.isParentComment();
    }
}
