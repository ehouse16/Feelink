package com.diary.feelink.ai.dto.response;

import java.util.List;

public record OpenAIResponse(
        List<Choice> choices
) {
    public record Choice(
            Message message
    ){
        public record Message(
                String content
        ){}
    }
}
