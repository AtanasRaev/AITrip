package com.aitrip.service.impl;

import com.aitrip.database.dto.PromptDTO;
import com.aitrip.database.repository.PromptRepository;
import com.aitrip.service.PromptService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PromptServiceImpl implements PromptService {
    private final PromptRepository promptRepository;
    private final ModelMapper modelMapper;

    public PromptServiceImpl(PromptRepository promptRepository,
                             ModelMapper modelMapper) {
        this.promptRepository = promptRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PromptDTO getPromptByPlanName(String planName) {
        return this.promptRepository.findByPlanName(planName)
                .map(prompt -> this.modelMapper.map(prompt, PromptDTO.class))
                .orElse(null);
    }
}
