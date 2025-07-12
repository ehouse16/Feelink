package com.diary.feelink.member.service;

import com.diary.feelink.exception.DomainException;
import com.diary.feelink.exception.ErrorType;
import com.diary.feelink.member.dto.request.LogInRequest;
import com.diary.feelink.member.dto.request.SignUpRequest;
import com.diary.feelink.member.dto.response.LogInResponse;
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
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void register(SignUpRequest signUpRequest) {
        if(memberRepository.existsByEmail(signUpRequest.email()))
            throw new DomainException(ErrorType.DUPLICATE_EMAIL);

        if(memberRepository.existsByNickname(signUpRequest.nickname()))
            throw new DomainException(ErrorType.DUPLICATE_NICKNAME);

        String encodedPassword = passwordEncoder.encode(signUpRequest.password());

        Member member = Member.builder()
                .email(signUpRequest.email())
                .password(encodedPassword)
                .nickname(signUpRequest.nickname())
                .build();

        memberRepository.save(member);
    }

    @Override
    public LogInResponse logIn(LogInRequest logInRequest) {
        Member member = memberRepository.findByEmail(logInRequest.email())
                .orElseThrow(() -> new DomainException(ErrorType.MEMBER_NOT_FOUND));

        if(!passwordEncoder.matches(logInRequest.password(), member.getPassword())){
            throw new DomainException(ErrorType.INVALID_PASSWORD);
        }

        return LogInResponse.fromEntity(member);
    }
}
