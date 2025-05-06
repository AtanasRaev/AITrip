package com.aitrip.web;

import com.aitrip.database.dto.PlanCreateDTO;
import com.aitrip.service.PlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/plans")
public class PlanController {
    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping("/create")
    public ResponseEntity<?> createPlan(@RequestBody PlanCreateDTO planCreateDTO) {
        return ResponseEntity.ok().body(Map.of(
                "message", "success",
                "plan", planService.savePlan(planCreateDTO))
        );
    }
}
