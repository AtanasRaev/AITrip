package com.aitrip.service;

import com.aitrip.database.dto.plan.PlanCreateDTO;
import com.aitrip.database.dto.plan.PlanPageDTO;
import com.amadeus.resources.FlightOfferSearch;
import com.amadeus.resources.HotelOffer;

public interface OpenAIService {
    PlanPageDTO createPlan(PlanCreateDTO planCreateDTO, FlightOfferSearch[] flights, HotelOffer[] hotelOffers);
}
