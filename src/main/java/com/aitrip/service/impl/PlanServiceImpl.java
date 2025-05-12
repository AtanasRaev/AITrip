package com.aitrip.service.impl;

import com.aitrip.database.dto.plan.PlanCreateDTO;
import com.aitrip.database.dto.plan.PlanPageDTO;
import com.aitrip.database.repository.PlanRepository;
import com.aitrip.service.OpenAIService;
import com.aitrip.service.PlanService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PlanServiceImpl implements PlanService {
    private final PlanRepository planRepository;
    private final OpenAIService openAiService;
    private final ModelMapper modelMapper;

    public PlanServiceImpl(PlanRepository planRepository, OpenAIService openAiService,
                           ModelMapper modelMapper) {
        this.planRepository = planRepository;
        this.openAiService = openAiService;
        this.modelMapper = modelMapper;
    }

    @Override
    public PlanPageDTO savePlan(PlanCreateDTO planCreateDTO) {
        //TODO: Finish the implementation
        this.openAiService.createPlan(planCreateDTO);
        return null;
    }
}
