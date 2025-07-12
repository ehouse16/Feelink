package com.diary.feelink.diary.repository;

import com.diary.feelink.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findAllByMemberId(Long memberId);
}
