package com.aitrip.service;

import com.aitrip.config.AmadeusEnvironment;
import com.aitrip.database.dto.flight.response.FlightResponseDTO;
import com.aitrip.database.dto.hotel.response.offers.HotelOfferResponseDTO;
import com.aitrip.database.dto.plan.PlanCreateDTO;

public interface AmadeusService {
    /**
     * Gets flights based on the plan creation data and environment.
     *
     * @param planCreateDTO the plan creation data
     * @param environment the Amadeus environment
     * @return the flight response
     */
    FlightResponseDTO getFlights(PlanCreateDTO planCreateDTO, AmadeusEnvironment environment);


    /**
     * Gets hotel offers based on the plan creation data and environment.
     *
     * @param planCreateDTO the plan creation data
     * @param environment the Amadeus environment
     * @return the hotel offer response
     */
    HotelOfferResponseDTO getHotelOffers(PlanCreateDTO planCreateDTO, AmadeusEnvironment environment);

}
