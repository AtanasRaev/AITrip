package com.aitrip.service.impl;

import com.aitrip.config.AmadeusEnvironment;
import com.aitrip.database.dto.plan.PlanCreateDTO;
import com.aitrip.database.dto.plan.PlanPageDTO;
import com.aitrip.database.repository.PlanRepository;
import com.aitrip.service.AmadeusService;
import com.aitrip.service.OpenAIService;
import com.aitrip.service.PlanService;
import com.amadeus.resources.FlightOfferSearch;
import com.amadeus.resources.HotelOffer;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PlanServiceImpl implements PlanService {
    private final PlanRepository planRepository;
    private final OpenAIService openAiService;
    private final AmadeusService amadeusService;
    private final ModelMapper modelMapper;


    @Override
    public PlanPageDTO savePlan(PlanCreateDTO planCreateDTO, AmadeusEnvironment environment) {
        //TODO: Finish the implementation
        FlightOfferSearch[] flights = this.amadeusService.getFlights(planCreateDTO, environment);
        HotelOffer[] hotelOffers = this.amadeusService.getHotelOffers(planCreateDTO, environment);

        if (hotelOffers.length == 0) {
            return null;
        }

        PlanPageDTO plan = this.openAiService.createPlan(planCreateDTO, flights, hotelOffers);
        return null;
    }
}
