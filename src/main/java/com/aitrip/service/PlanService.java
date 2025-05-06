package com.aitrip.service;

import com.aitrip.database.dto.PlanCreateDTO;
import com.aitrip.database.dto.PlanPageDTO;

public interface PlanService {
    PlanPageDTO savePlan(PlanCreateDTO planCreateDTO);
}
