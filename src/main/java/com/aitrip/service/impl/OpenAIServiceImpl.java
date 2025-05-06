package com.aitrip.service.impl;

import com.aitrip.database.dto.OpenAIResponseDTO;
import com.aitrip.database.dto.PlanCreateDTO;
import com.aitrip.database.dto.PlanPageDTO;
import com.aitrip.database.dto.PromptDTO;
import com.aitrip.service.OpenAIService;
import com.openai.client.OpenAIClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class OpenAIServiceImpl implements OpenAIService {
    private final OpenAIClient client;
    private final ModelMapper modelMapper;

    public OpenAIServiceImpl(OpenAIClient client,
                             ModelMapper modelMapper) {
        this.client = client;
        this.modelMapper = modelMapper;
    }

    @Override
    public PlanPageDTO createPlan(PlanCreateDTO planCreateDTO) {
        //TODO: incomplete implementation
        return null;
    }

    private OpenAIResponseDTO sendPrompt(PromptDTO promptDTO) {
        if (promptDTO == null) {
            throw new IllegalArgumentException("PromptDTO cannot be null");
        }

        if (promptDTO.getUserPrompt() == null || promptDTO.getUserPrompt().isEmpty()) {
            throw new IllegalArgumentException("User prompt cannot be null or empty");
        }

        ChatCompletionCreateParams.Builder paramsBuilder = ChatCompletionCreateParams.builder()
                .model(ChatModel.GPT_4_1);

        if (promptDTO.getSystemPrompt() != null && !promptDTO.getSystemPrompt().isEmpty()) {
            paramsBuilder.addSystemMessage(promptDTO.getSystemPrompt());
        }

        paramsBuilder.addUserMessage(promptDTO.getUserPrompt());

        ChatCompletion chatCompletion = this.client.chat().completions().create(paramsBuilder.build());
        return this.modelMapper.map(chatCompletion, OpenAIResponseDTO.class);
    }
}
