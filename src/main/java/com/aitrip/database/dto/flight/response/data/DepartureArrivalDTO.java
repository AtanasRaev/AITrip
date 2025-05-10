package com.aitrip.database.dto.flight.response.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class DepartureArrivalDTO {
    private String iataCode;

    private String terminal;

    @JsonFormat(
            pattern = "yyyy-MM-dd['T'HH:mm:ssXXX]",
            timezone = "UTC"
    )
    private OffsetDateTime at;
}
