package com.aitrip.database.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Service
@Entity
@Table(name = "plans")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String origin;

    @Column
    private String destination;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false)
    private BigDecimal budget;

    @Column(nullable = false)
    private int adults;

    @Column(nullable = false)
    private int children;

    @Column(name = "travel_interests")
    private String travelInterests;

    @Column(name = "flight_preference")
    private String flightPreference;

    @Column
    private String accomodation;

    @Column(name = "visa_requirements")
    private String visaRequirements;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @CreationTimestamp
    @Column(name = "created_ad", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
    private List<AIUsage> aiUsages;

    //TODO: USER relation to the plan when we setup the security
}
