package com.aitrip.web;

import com.aitrip.database.dto.plan.PlanCreateDTO;
import com.aitrip.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/plans")
public class PlanController {
    private final PlanService planService;


    @PostMapping("/create")
    public ResponseEntity<?> createPlan(@RequestBody PlanCreateDTO planCreateDTO) {
        return ResponseEntity.ok().body(Map.of(
                "message", "success",
                "plan", this.planService.savePlan(planCreateDTO))
        );
    }
}
