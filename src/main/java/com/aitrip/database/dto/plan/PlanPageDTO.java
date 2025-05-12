package com.aitrip.database.dto.plan;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PlanPageDTO {
    private long id;

    private String origin;

    private String destination;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private BigDecimal budget;

    private int adults;

    private int children;

    private String travelInterests;

    private String flightPreference;

    private String accomodation;

    private String visaRequirements;

    private String notes;
}
