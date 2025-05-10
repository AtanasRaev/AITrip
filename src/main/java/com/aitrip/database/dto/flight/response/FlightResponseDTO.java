package com.aitrip.database.dto.flight.response;

import com.aitrip.database.dto.flight.response.data.FlightDataDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FlightResponseDTO {
    private FlightCountDTO meta;

    private List<FlightDataDTO> data;

    private DictionaryDTO dictionaries;
}
