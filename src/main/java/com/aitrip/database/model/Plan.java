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

    @Column
    private String origin;

    @Column
    private String destination;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column
    private BigDecimal budget;

    @Column(nullable = false)
    private int adults;

    @Column
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
