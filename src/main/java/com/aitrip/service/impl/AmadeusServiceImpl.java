package com.aitrip.service.impl;

import com.aitrip.config.AmadeusEnvironment;
import com.aitrip.database.dto.flight.FlightRequestDTO;
import com.aitrip.database.dto.plan.PlanCreateDTO;
import com.aitrip.exception.external.amadeus.*;
import com.aitrip.service.AmadeusService;
import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.FlightOfferSearch;
import com.amadeus.resources.Hotel;
import com.amadeus.resources.HotelOffer;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AmadeusServiceImpl implements AmadeusService {
    private final Gson gson;
    private final Amadeus amadeusProd;
    private final Amadeus amadeusTest;

    public AmadeusServiceImpl(Gson gson,
                              @Qualifier("amadeusProd") Amadeus amadeusProd,
                              @Qualifier("amadeusTest") Amadeus amadeusTest) {
        this.gson = gson;
        this.amadeusProd = amadeusProd;
        this.amadeusTest = amadeusTest;
    }

    @Override
    public FlightOfferSearch[] getFlights(PlanCreateDTO planCreateDTO, AmadeusEnvironment environment) {
        try {
            Amadeus amadeus = this.getAmadeus(environment);
            FlightRequestDTO flightRequest = createFlightRequest(planCreateDTO);
            String jsonBody = this.gson.toJson(flightRequest);

            FlightOfferSearch[] response = amadeus.shopping.flightOffersSearch.post(jsonBody);

            if (response == null || response.length == 0) {
                throw new NoFlightsAvailableException(
                        "No flights available for the selected dates",
                        planCreateDTO.getOrigin(),
                        planCreateDTO.getDestination(),
                        planCreateDTO.getStartDate().toString(),
                        planCreateDTO.getEndDate().toString()
                );
            }

            return response;
        } catch (Exception ex) {
            throw new FlightSearchFailedException("Flight search failed due to an error while calling Amadeus API", ex);
        }
    }

    @Override
    public HotelOffer[] getHotelOffers(PlanCreateDTO planCreateDTO, AmadeusEnvironment environment) {
        try {
            Amadeus amadeus = this.getAmadeus(environment);

            Hotel[] hotelResponse = this.getHotels(planCreateDTO.getDestination(), environment);

            if (hotelResponse.length == 0) {
                return new HotelOffer[0];
            }

            String hotelIds = Arrays.stream(hotelResponse)
                    .map(Hotel::getHotelId)
                    .collect(Collectors.joining(","));

            HotelOffer[] hotelOffers = amadeus.shopping.hotelOffers.get(Params
                    .with("hotelIds", hotelIds)
                    .and("adults", planCreateDTO.getAdults())
                    .and("checkInDate", planCreateDTO.getStartDate().toString())
                    .and("checkOutDate", planCreateDTO.getEndDate().toString())
                    .and("roomQuantity", 1)
            );

            if (hotelOffers == null || hotelOffers.length == 0) {
                return new HotelOffer[0];
            }

            return hotelOffers;
        } catch (Exception ex) {
            throw new HotelOfferSearchFailedException(
                    "Failed to search hotel offers for city: " + planCreateDTO.getDestination(), ex
            );
        }
    }

    //TODO: Consider if we need to add logic to catch an error where the access token has expired and we need to manually renew it.
    private FlightRequestDTO createFlightRequest(PlanCreateDTO planCreateDTO) {
        FlightRequestDTO flightRequest = new FlightRequestDTO();
        FlightRequestDTO.DestinationDTO origin = new FlightRequestDTO.DestinationDTO(
                "1",
                planCreateDTO.getOrigin(),
                planCreateDTO.getDestination(),
                new FlightRequestDTO.DepartureDateDTO(planCreateDTO.getStartDate())
        );

        flightRequest.getOriginDestinations().add(origin);

        if (planCreateDTO.getDestination() != null && !planCreateDTO.getDestination().isEmpty()) {
            FlightRequestDTO.DestinationDTO destination = new FlightRequestDTO.DestinationDTO(
                    "2",
                    planCreateDTO.getDestination(),
                    planCreateDTO.getOrigin(),
                    new FlightRequestDTO.DepartureDateDTO(planCreateDTO.getEndDate())
            );
            flightRequest.getOriginDestinations().add(destination);
        }

        for (int i = 0; i < planCreateDTO.getAdults(); i++) {
            FlightRequestDTO.Traveler adult = new FlightRequestDTO.Traveler(
                    String.format("%d", i),
                    "ADULT"
            );
            flightRequest.getTravelers().add(adult);
        }

        for (int i = 1; i <= planCreateDTO.getChildren(); i++) {
            FlightRequestDTO.Traveler child = new FlightRequestDTO.Traveler(
                    String.format("%d", i + planCreateDTO.getAdults()),
                    "CHILD"
            );
            flightRequest.getTravelers().add(child);
        }

        flightRequest.setSources(List.of("GDS"));

        return flightRequest;
    }

    private Hotel[] getHotels(String cityCode, AmadeusEnvironment environment) {
        Amadeus amadeus = this.getAmadeus(environment);

        try {
            Hotel[] hotels = amadeus.referenceData.locations.hotels.byCity.get(
                    Params.with("cityCode", cityCode)
            );
            if (hotels == null || hotels.length == 0) {
                return new Hotel[0];
            }
            return hotels;
        } catch (ResponseException e) {
            throw new HotelSearchFailedException(
                    "Failed to fetch hotels for city code: " + cityCode, e
            );
        }
    }

    private Amadeus getAmadeus(AmadeusEnvironment env) {
        return switch (env) {
            case PRODUCTION -> amadeusProd;
            case TEST -> amadeusTest;
        };
    }
}
