package com.aitrip.database.dto.plan;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PlanCreateDTO {
    @NotBlank(message = "Plan name is required")
    private String planName;

    @NotBlank(message = "Origin is required")
    private String origin;

    private String destination;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @NotNull(message = "Budget is required")
    @Min(value = 0, message = "Budget must be greater than or equal to 0")
    private BigDecimal budget;

    @Min(value = 1, message = "At least one adult is required")
    private int adults;

    @Min(value = 0, message = "Number of children cannot be negative")
    private int children;
}
