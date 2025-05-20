package com.aitrip.database.model;

import com.aitrip.utils.ChatModelConverter;
import com.aitrip.utils.ReasoningEffortConverter;
import com.openai.models.ChatModel;
import com.openai.models.ReasoningEffort;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "prompts")
public class Prompt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "plan_name", nullable = false, unique = true)
    private String planName;

    @Column(name = "system_prompt", nullable = false, columnDefinition = "TEXT")
    private String systemPrompt;

    @Column(name = "user_prompt", nullable = false, columnDefinition = "TEXT")
    private String userPrompt;

    @Convert(converter = ChatModelConverter.class)
    @Column(nullable = false)
    private ChatModel model;

    @Column(name = "max_completion_tokens")
    private Long maxCompletionTokens;

    private Double temperature;

    @Column(name = "presence_penalty")
    private Double presencePenalty;

    @Column(name = "frequency_penalty")
    private Double frequencyPenalty;

    @Column(name = "top_p")
    private Double topP;

    @Convert(converter = ReasoningEffortConverter.class)
    @Column(name = "reasoning_effort")
    private ReasoningEffort reasoningEffort;
}
