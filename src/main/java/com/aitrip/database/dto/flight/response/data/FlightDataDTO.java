package com.aitrip.database.dto.flight.response.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class FlightDataDTO {
    private String id;

    private boolean oneWay;

    @JsonFormat(
            pattern = "yyyy-MM-dd['T'HH:mm:ssXXX]",
            timezone = "UTC"
    )
    private OffsetDateTime lastTicketingDateTime;

    private Integer numberOfBookableSeats;

    private List<Itinerary> itineraries;
}
