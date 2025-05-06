package com.aitrip.database.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PromptDTO {
    private String systemPrompt;

    private String userPrompt;
}
