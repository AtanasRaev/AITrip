package com.aitrip.database.model;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Getter
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
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

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
}
