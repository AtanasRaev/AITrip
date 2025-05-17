package com.aitrip.service.impl;

import com.aitrip.database.dto.openAI.OpenAIResponseDTO;
import com.aitrip.database.dto.plan.PlanCreateDTO;
import com.aitrip.database.dto.plan.PlanPageDTO;
import com.aitrip.database.dto.prompt.PromptDTO;
import com.aitrip.exception.plan.NullPlanCreateDTOException;
import com.aitrip.exception.prompt.EmptySystemPromptException;
import com.aitrip.exception.prompt.EmptyUserPromptException;
import com.aitrip.exception.prompt.NullPromptException;
import com.aitrip.service.OpenAIService;
import com.aitrip.service.PromptService;
import com.openai.client.OpenAIClient;
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
    public PlanPageDTO createPlan(PlanCreateDTO planCreateDTO) {
        if (planCreateDTO == null) {
            throw new NullPlanCreateDTOException();
        }

        PromptDTO prompt = getAndValidatePrompt(planCreateDTO.getPlanName());

        String userPrompt = setVariables(planCreateDTO, prompt.getUserPrompt());
        String systemPrompt = setVariables(planCreateDTO, prompt.getSystemPrompt());

        prompt.setUserPrompt(userPrompt);
        prompt.setSystemPrompt(systemPrompt);

        OpenAIResponseDTO response = sendPrompt(prompt);

        //TODO: Finish the implementation
        return null;
    }

    private OpenAIResponseDTO sendPrompt(PromptDTO promptDTO) {
        validatePrompt(promptDTO);

        ChatCompletionCreateParams params = setupOpenAIParams(promptDTO);
        ChatCompletion chatCompletion = this.client.chat().completions().create(params);
        return this.modelMapper.map(chatCompletion, OpenAIResponseDTO.class);
    }

    private static ChatCompletionCreateParams setupOpenAIParams(PromptDTO promptDTO) {
        ChatCompletionCreateParams.Builder paramsBuilder = ChatCompletionCreateParams.builder()
                .model(promptDTO.getModel())
                .addSystemMessage(promptDTO.getSystemPrompt())
                .addUserMessage(promptDTO.getUserPrompt());

        if (promptDTO.getMaxCompletionsTokens() != null) {
            paramsBuilder.maxCompletionTokens(promptDTO.getMaxCompletionsTokens());
        }

        if (promptDTO.getTemperature() != null) {
            paramsBuilder.temperature(promptDTO.getTemperature());
        }

        if (promptDTO.getTopP() != null) {
            paramsBuilder.topP(promptDTO.getTopP());
        }

        if (promptDTO.getFrequencyPenalty() != null) {
            paramsBuilder.frequencyPenalty(promptDTO.getFrequencyPenalty());
        }

        if (promptDTO.getPresencePenalty() != null) {
            paramsBuilder.presencePenalty(promptDTO.getPresencePenalty());
        }

        if (promptDTO.getReasoningEffort() != null) {
            paramsBuilder.reasoningEffort(promptDTO.getReasoningEffort());
        }

        return paramsBuilder.build();
    }

    private PromptDTO getAndValidatePrompt(String planName) {
        return this.promptService.getPromptByPlanName(planName);
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

    private String setVariables(PlanCreateDTO planCreateDTO, String prompt) {
        Map<String, String> replacements = buildReplacementsMap(planCreateDTO);

        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            String placeholder = "{" + entry.getKey() + "}";
            String value = entry.getValue();

            if (value != null) {
                prompt = prompt.replace(placeholder, value);
            }
        }

        return prompt;
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
