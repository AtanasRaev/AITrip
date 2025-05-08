package com.aitrip.service.impl;

import com.aitrip.database.dto.OpenAIResponseDTO;
import com.aitrip.database.dto.PlanCreateDTO;
import com.aitrip.database.dto.PlanPageDTO;
import com.aitrip.database.dto.PromptDTO;
import com.aitrip.exception.EmptySystemPromptException;
import com.aitrip.exception.EmptyUserPromptException;
import com.aitrip.exception.NullPlanCreateDTOException;
import com.aitrip.exception.NullPlanNameException;
import com.aitrip.exception.NullPromptException;
import com.aitrip.service.OpenAIService;
import com.aitrip.service.PromptService;
import com.openai.client.OpenAIClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OpenAIServiceImpl implements OpenAIService {
    private final OpenAIClient client;
    private final PromptService promptService;
    private final ModelMapper modelMapper;

    public OpenAIServiceImpl(OpenAIClient client,
                             PromptService promptService,
                             ModelMapper modelMapper) {
        this.client = client;
        this.promptService = promptService;
        this.modelMapper = modelMapper;
    }

    @Override
    public PlanPageDTO createPlan(PlanCreateDTO planCreateDTO, String planName) {
        if (planCreateDTO == null) {
            throw new NullPlanCreateDTOException();
        }

        if (planName == null) {
            throw new NullPlanNameException();
        }

        PromptDTO prompt = getAndValidatePrompt(planName);

        String userPrompt = setVariables(planCreateDTO, prompt);
        prompt.setUserPrompt(userPrompt);

        OpenAIResponseDTO response = sendPrompt(prompt);

        return null;
    }

    private OpenAIResponseDTO sendPrompt(PromptDTO promptDTO) {
        validatePrompt(promptDTO);

        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .model(ChatModel.GPT_4_1)
                .addSystemMessage(promptDTO.getSystemPrompt())
                .addUserMessage(promptDTO.getUserPrompt())
                .build();

        ChatCompletion chatCompletion = this.client.chat().completions().create(params);
        return this.modelMapper.map(chatCompletion, OpenAIResponseDTO.class);
    }

    private PromptDTO getAndValidatePrompt(String planName) {
        PromptDTO prompt = this.promptService.getPromptByPlanName(planName);

        if (prompt == null) {
            throw new NullPromptException("Prompt not found for plan name: " + planName);
        }

        return prompt;
    }

    private void validatePrompt(PromptDTO promptDTO) {
        if (promptDTO == null) {
            throw new NullPromptException();
        }

        if (promptDTO.getUserPrompt() == null || promptDTO.getUserPrompt().isEmpty()) {
            throw new EmptyUserPromptException();
        }

        if (promptDTO.getSystemPrompt() == null || promptDTO.getSystemPrompt().isEmpty()) {
            throw new EmptySystemPromptException();
        }
    }

    private String setVariables(PlanCreateDTO planCreateDTO, PromptDTO prompt) {
        String userPrompt = prompt.getUserPrompt();
        Map<String, String> replacements = buildReplacementsMap(planCreateDTO);

        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            String placeholder = "{" + entry.getKey() + "}";
            String value = entry.getValue();

            if (value != null) {
                userPrompt = userPrompt.replace(placeholder, value);
            }
        }

        return userPrompt;
    }

    private Map<String, String> buildReplacementsMap(PlanCreateDTO planCreateDTO) {
        Map<String, String> replacements = new HashMap<>();

        replacements.put("origin", planCreateDTO.getOrigin());
        replacements.put("destination", planCreateDTO.getDestination());
        replacements.put("startDate", planCreateDTO.getStartDate() != null ?
                planCreateDTO.getStartDate().toString() : null);
        replacements.put("endDate", planCreateDTO.getEndDate() != null ?
                planCreateDTO.getEndDate().toString() : null);
        replacements.put("budget", planCreateDTO.getBudget() != null ?
                planCreateDTO.getBudget().toString() : null);

        replacements.put("adults", String.valueOf(planCreateDTO.getAdults()));
        replacements.put("children", String.valueOf(planCreateDTO.getChildren()));

        return replacements;
    }
}
