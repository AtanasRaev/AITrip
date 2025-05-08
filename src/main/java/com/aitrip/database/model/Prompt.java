package com.aitrip.database.model;

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

    @Column(name = "plan_name", nullable = false)
    private String planName;

    @Column(name = "system_prompt", nullable = false)
    private String systemPrompt;

    @Column(name = "user_prompt", nullable = false)
    private String userPrompt;

    @Column(nullable = false)
    private String model;
}
