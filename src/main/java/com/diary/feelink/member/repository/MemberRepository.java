package com.diary.feelink.member.repository;

import com.diary.feelink.member.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    Optional<Member> findByEmail(@Email(message = "올바른 이메일 형식이 아닙니다.") String email);
    Optional<Member> findByNickname(String nickname);
}
