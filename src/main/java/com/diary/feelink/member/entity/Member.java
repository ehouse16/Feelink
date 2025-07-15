package com.diary.feelink.member.entity;

import com.diary.feelink.common.BaseEntity;
import com.diary.feelink.diary.entity.Diary;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @OneToMany(mappedBy = "member")
    private List<Diary> diaries = new ArrayList<>();

    @Builder
    public Member(String email,String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
}
