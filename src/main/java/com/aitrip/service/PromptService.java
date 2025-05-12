package com.aitrip.service;

import com.aitrip.database.dto.PromptCreateDTO;
import com.aitrip.database.dto.PromptDTO;
import com.aitrip.database.dto.PromptEditDTO;

public interface PromptService {
    PromptDTO getPromptByPlanName(String planName);

    PromptDTO getPromptById(Long id);

    PromptDTO createPrompt(PromptCreateDTO promptCreateDTO);

    PromptDTO editPromptById(Long id, PromptEditDTO promptEditDTO);

    Long deletePromptById(Long id);
}
