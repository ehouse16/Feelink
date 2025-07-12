package com.diary.feelink.member.annotation;

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
public class LoginUserResolver implements HandlerMethodArgumentResolver {
    private final MemberRepository memberRepository;
    private final RedisTemplate<String, Object> redisTemplate;


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUser.class)
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

        if(session == null) {return null;}

        String sessionToken = (String) session.getAttribute("LOGIN_TOKEN");
        if(sessionToken == null) {return null;}

        String nickname = (String) redisTemplate.opsForValue().get("session:" + sessionToken);
        if(nickname == null) {return null;}

        return memberRepository.findByNickname(nickname).orElse(null);
    }
}
