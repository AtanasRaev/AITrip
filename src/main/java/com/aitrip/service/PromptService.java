package com.aitrip.service;

import com.aitrip.database.dto.prompt.PromptCreateDTO;
import com.aitrip.database.dto.prompt.PromptDTO;
import com.aitrip.database.dto.prompt.PromptEditDTO;

import java.util.List;

public interface PromptService {
    PromptDTO getPromptByPlanName(String planName);

    PromptDTO getPromptById(Long id);

    PromptDTO createPrompt(PromptCreateDTO promptCreateDTO);

    PromptDTO editPromptById(Long id, PromptEditDTO promptEditDTO);

    Long deletePromptById(Long id);

    List<PromptDTO> getAll();
}
