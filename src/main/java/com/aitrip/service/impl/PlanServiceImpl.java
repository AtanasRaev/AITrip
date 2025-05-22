package com.aitrip.service.impl;

import com.aitrip.database.dto.plan.PlanCreateDTO;
import com.aitrip.database.dto.plan.PlanPageDTO;
import com.aitrip.database.repository.PlanRepository;
import com.aitrip.service.OpenAIService;
import com.aitrip.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PlanServiceImpl implements PlanService {
    private final PlanRepository planRepository;
    private final OpenAIService openAiService;
    private final ModelMapper modelMapper;


    @Override
    public PlanPageDTO savePlan(PlanCreateDTO planCreateDTO) {
        //TODO: Finish the implementation
        this.openAiService.createPlan(planCreateDTO);
        return null;
    }
}
