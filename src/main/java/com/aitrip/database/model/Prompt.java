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

    @Column(name = "plan_name")
    private String planName;

    @Column(name = "system_prompt")
    private String systemPrompt;

    @Column(name = "user_prompt")
    private String userPrompt;
}
