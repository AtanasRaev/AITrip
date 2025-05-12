package com.aitrip.service.impl;

import com.aitrip.database.dto.PromptCreateDTO;
import com.aitrip.database.dto.PromptDTO;
import com.aitrip.database.dto.PromptEditDTO;
import com.aitrip.database.model.Prompt;
import com.aitrip.database.repository.PromptRepository;
import com.aitrip.exception.NullPromptException;
import com.aitrip.exception.PromptNotFoundException;
import com.aitrip.service.PromptService;
import com.openai.models.ChatModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public PromptDTO getPromptById(Long id) {
        return this.promptRepository.findById(id)
                .map(p -> this.modelMapper.map(p, PromptDTO.class))
                .orElse(null);
    }

    @Override
    public PromptDTO createPrompt(PromptCreateDTO promptCreateDTO) {
        if (promptCreateDTO == null) {
            throw new NullPromptException();
        }

        Prompt prompt = this.modelMapper.map(promptCreateDTO, Prompt.class);
        ChatModel model = promptCreateDTO.getModel();
        setModelParams(model, prompt);

        this.promptRepository.save(prompt);
        return this.modelMapper.map(prompt, PromptDTO.class);
    }

    @Override
    public PromptDTO editPromptById(Long id, PromptEditDTO promptEditDTO) {
        Optional<Prompt> optional = this.promptRepository.findById(id);
        if (optional.isEmpty()) {
            throw new PromptNotFoundException("Prompt not found with ID: " + id);
        }

        Prompt prompt = optional.get();
        this.modelMapper.map(promptEditDTO, prompt);

        ChatModel model = promptEditDTO.getModel();
        setModelParams(model, prompt);
        this.promptRepository.save(prompt);

        return this.modelMapper.map(prompt, PromptDTO.class);
    }

    @Override
    public Long deletePromptById(Long id) {
        Optional<Prompt> optional = this.promptRepository.findById(id);
        if (optional.isEmpty()) {
            throw new PromptNotFoundException("Prompt not found with ID: " + id);
        }

        this.promptRepository.deleteById(id);
        return id;
    }

    private static void setModelParams(ChatModel model, Prompt prompt) {
        if (model != ChatModel.O3 &&
                model != ChatModel.O3_MINI &&
                model != ChatModel.O4_MINI) {
            prompt.setReasoningEffort(null);
        } else {
            prompt.setTemperature(null);
            prompt.setFrequencyPenalty(null);
            prompt.setMaxCompletionsTokens(null);
            prompt.setPresencePenalty(null);
            prompt.setTopP(null);
        }
    }
}
