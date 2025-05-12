package com.aitrip.database.model;

import com.aitrip.utils.ReasoningEffortConverter;
import com.openai.models.ChatModel;
import com.openai.models.ReasoningEffort;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "ai_usages")
public class AIUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Convert(converter = ReasoningEffortConverter.class)
    @Column(nullable = false)
    private ChatModel model;

    @Column(name = "prompt_tokens", nullable = false)
    private int promptTokens;

    @Column(name = "completion_tokens", nullable = false)
    private int completionTokens;

    @Column(name = "total_tokens", nullable = false)
    private int totalTokens;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "max_completions_tokens")
    private Long maxCompletionsTokens;

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

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;
}
