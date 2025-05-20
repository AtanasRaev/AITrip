package com.aitrip.service;

import com.aitrip.config.AmadeusEnvironment;
import com.aitrip.database.dto.plan.PlanCreateDTO;
import com.amadeus.resources.FlightOfferSearch;
import com.amadeus.resources.HotelOffer;

public interface AmadeusService {
    /**
     * Gets flights based on the plan creation data and environment.
     *
     * @param planCreateDTO the plan creation data
     * @param environment the Amadeus environment
     * @return the flight response
     */
    FlightOfferSearch[] getFlights(PlanCreateDTO planCreateDTO, AmadeusEnvironment environment);


    /**
     * Gets hotel offers based on the plan creation data and environment.
     *
     * @param planCreateDTO the plan creation data
     * @param environment the Amadeus environment
     * @return the hotel offer response
     */
    HotelOffer[] getHotelOffers(PlanCreateDTO planCreateDTO, AmadeusEnvironment environment);

}
