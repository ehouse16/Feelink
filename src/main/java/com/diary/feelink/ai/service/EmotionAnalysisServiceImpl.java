package com.diary.feelink.ai.service;

import com.diary.feelink.ai.dto.request.OpenAIRequest;
import com.diary.feelink.ai.dto.response.EmotionResult;
import com.diary.feelink.ai.dto.response.OpenAIResponse;
import com.diary.feelink.diary.entity.EmotionType;
import com.diary.feelink.exception.DomainException;
import com.diary.feelink.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmotionAnalysisServiceImpl implements EmotionAnalysisService {
    @Value("${openai.api-key}")
    private String apiKey;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api-url}")
    private String url;

    private final RestTemplate restTemplate;

    public EmotionResult analyzeEmotion(String diaryContent){
        try{
            String prompt = String.format(
                            "다음 일기 내용을 읽고 주요 감정을 분석해주세요. " +
                            "반드시 다음 형식으로만 답변하세요: '감정:신뢰도' (예: 행복:0.8)\n" +
                            "감정은 다음 중 하나: 행복, 슬픔, 분노, 불안, 놀람, 혐오, 무감정\n" +
                            "신뢰도는 0.0~1.0 사이의 소수점 한 자리 숫자\n\n" +
                            "일기 내용: %s", diaryContent);

            OpenAIRequest request = new OpenAIRequest(model, List.of(new OpenAIRequest.Message("user", prompt)));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            HttpEntity<OpenAIRequest> requestEntity = new HttpEntity<>(request, headers);

            OpenAIResponse response = restTemplate.postForObject(
                    url,
                    requestEntity,
                    OpenAIResponse.class
            );

            String result = response.choices().get(0).message().content().trim();

            return parseEmotionResult(result);
        } catch (Exception e){
            log.error(e.getMessage());
            throw new DomainException(ErrorType.CANNOT_PARSE);
        }
    }

    private EmotionResult parseEmotionResult(String result) {
        try {
            // "행복:1.0", "행복: 1.0", "행복 (1.0)" 등 다양한 형식 허용
            Pattern pattern = Pattern.compile("([가-힣]+)[\\s:()]*([0-9]*\\.?[0-9]+)");
            Matcher matcher = pattern.matcher(result.trim());

            if (matcher.find()) {
                String emotionName = matcher.group(1).trim();
                double confidence = Double.parseDouble(matcher.group(2).trim());

                EmotionType emotionType = mapToEmotionType(emotionName);
                return new EmotionResult(emotionType, confidence);
            }
        } catch (Exception e) {
            log.info("결과값: {} " + result);
            log.error("Failed to parse emotion result: {}", e.getMessage());
            throw new DomainException(ErrorType.CANNOT_PARSE);
        }

        return new EmotionResult(EmotionType.NEUTRAL, 0.5);
    }

    private EmotionType mapToEmotionType(String emotionName) {
        return Arrays.stream(EmotionType.values())
                .filter(emotion -> emotion.getEmotionName().equals(emotionName))
                .findFirst()
                .orElseThrow(() -> new DomainException(ErrorType.CANNOT_PARSE));
    }
}
