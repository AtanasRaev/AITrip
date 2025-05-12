package com.aitrip.database.dto;

import com.openai.models.ChatModel;
import com.openai.models.ReasoningEffort;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PromptCreateDTO {
    @NotEmpty
    private String planName;

    @NotEmpty
    private String systemPrompt;

    @NotEmpty
    private String userPrompt;

    @NotNull
    private ChatModel model;

    @NotNull
    @Positive
    private Long maxCompletionsTokens;

    @DecimalMin("0.0")
    @DecimalMax("2.0")
    private Double temperature;

    @DecimalMin(value = "-2.0")
    @DecimalMax("2.0")
    private Double presencePenalty;

    @DecimalMin(value = "-2.0")
    @DecimalMax("2.0")
    private Double frequencyPenalty;

    @DecimalMin("0.0")
    @DecimalMax("1.0")
    private Double topP;

    private ReasoningEffort reasoningEffort;
}

