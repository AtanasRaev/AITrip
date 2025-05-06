package com.aitrip.service;

import com.aitrip.database.dto.PromptDTO;

public interface PromptService {
    PromptDTO getPromptByPlanName(String planName);
}
