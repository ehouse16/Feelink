package com.diary.feelink.ai.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record OpenAIRequest(
        String model,
        List<Message> messages,
        @JsonProperty("max_tokens")
        int maxTokens,
        double temperature
) {
    public OpenAIRequest(String model, List<Message> messages){
        this(model, messages, 100, 0.3);
    }

    public record Message(
            String role,
            String content
    ){}
}
