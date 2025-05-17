package com.aitrip.service;

import com.aitrip.database.dto.flight.response.FlightResponseDTO;
import com.aitrip.database.dto.hotel.response.HotelResponseDTO;
import com.aitrip.database.dto.plan.PlanCreateDTO;

public interface AmadeusService {
    FlightResponseDTO getFlights(PlanCreateDTO planCreateDTO);

    HotelResponseDTO getHotels(String destination);
}
