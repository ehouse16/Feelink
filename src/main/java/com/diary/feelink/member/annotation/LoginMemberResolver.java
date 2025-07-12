package com.diary.feelink.member.annotation;

import com.diary.feelink.exception.DomainException;
import com.diary.feelink.exception.ErrorType;
import com.diary.feelink.member.entity.Member;
import com.diary.feelink.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class LoginMemberResolver implements HandlerMethodArgumentResolver {
    private final MemberRepository memberRepository;
    private final RedisTemplate<String, Object> redisTemplate;


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMember.class)
                 && parameter.getParameterType().equals(Member.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpSession session = request.getSession(false);

        if(session == null) {
            throw new DomainException(ErrorType.SESSION_NOT_FOUND);
        }

        String sessionToken = (String) session.getAttribute("LOGIN_TOKEN");
        if(sessionToken == null) {
            throw new DomainException(ErrorType.LOGIN_REQUIRED);
        }

        String nickname = (String) redisTemplate.opsForValue().get("session:" + sessionToken);
        if(nickname == null) {
            throw new DomainException(ErrorType.SESSION_EXPIRED);
        }

        return memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new DomainException(ErrorType.MEMBER_NOT_FOUND));
    }
}
