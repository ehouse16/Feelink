package com.diary.feelink.member.service;

import com.diary.feelink.exception.DomainException;
import com.diary.feelink.exception.ErrorType;
import com.diary.feelink.member.dto.request.SignUpRequest;
import com.diary.feelink.member.entity.Member;
import com.diary.feelink.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public void register(SignUpRequest signUpRequest) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(signUpRequest.password());

        Member member = Member.builder()
                .email(signUpRequest.email())
                .password(encodedPassword)
                .nickname(signUpRequest.nickname())
                .build();

        if(memberRepository.existsByEmail(signUpRequest.email()))
            throw new DomainException(ErrorType.DUPLICATE_EMAIL);

        if(memberRepository.existsByNickname(signUpRequest.nickname()))
            throw new DomainException(ErrorType.DUPLICATE_NICKNAME);

        memberRepository.save(member);
    }
}
