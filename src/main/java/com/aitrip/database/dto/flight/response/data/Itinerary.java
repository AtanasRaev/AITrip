package com.aitrip.database.dto.flight.response.data;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.List;

@Getter
@Setter
public class Itinerary {
    private Duration duration;

    private List<SegmentDTO> segments;
}
