package com.aitrip.database.dto.prompt;

import com.openai.models.ChatModel;
import com.openai.models.ReasoningEffort;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PromptDTO {
    private Long id;

    private String planName;

    private String systemPrompt;

    private String userPrompt;

    private ChatModel model;

    private Long maxCompletionsTokens;

    private Double temperature;

    private Double presencePenalty;

    private Double frequencyPenalty;

    private Double topP;

    private ReasoningEffort reasoningEffort;
}
