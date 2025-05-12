package com.aitrip.service;

import com.aitrip.database.dto.plan.PlanCreateDTO;
import com.aitrip.database.dto.plan.PlanPageDTO;

public interface PlanService {
    PlanPageDTO savePlan(PlanCreateDTO planCreateDTO);
}
