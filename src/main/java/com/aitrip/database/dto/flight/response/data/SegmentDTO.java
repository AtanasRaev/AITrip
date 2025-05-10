package com.aitrip.database.dto.flight.response.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SegmentDTO {
    private DepartureArrivalDTO departure;

    private DepartureArrivalDTO arrival;

    private String carrierCode;

   private String flightNumber;

   private AircraftDTO aircraft;
}
