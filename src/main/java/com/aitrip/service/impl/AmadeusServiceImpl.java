package com.aitrip.service.impl;

import com.aitrip.database.dto.hotel.response.HotelResponseDTO;
import com.aitrip.database.dto.hotel.response.data.HotelDTO;
import com.aitrip.database.dto.hotel.response.offers.HotelOfferResponseDTO;
import com.aitrip.database.dto.plan.PlanCreateDTO;
import com.aitrip.database.dto.flight.request.FlightRequestDTO;
import com.aitrip.database.dto.flight.response.FlightResponseDTO;
import com.aitrip.exception.external.amadeus.NoFlightsAvailableException;
import com.aitrip.exception.external.amadeus.NoHotelOffersAvailableException;
import com.aitrip.service.AmadeusService;
import com.aitrip.service.AmadeusTokenService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class AmadeusServiceImpl implements AmadeusService {
    private static final String AMADEUS_URL = "https://test.api.amadeus.com/";

    private final RestClient restClient;
    private final AmadeusTokenService amadeusTokenService;

    public AmadeusServiceImpl(RestClient restClient,
                              AmadeusTokenService amadeusTokenService) {
        this.restClient = restClient;
        this.amadeusTokenService = amadeusTokenService;
    }

    @Override
    public FlightResponseDTO getFlights(PlanCreateDTO planCreateDTO) {
        FlightRequestDTO flightRequest = createFlightRequest(planCreateDTO);

        FlightResponseDTO response = this.restClient
                .post()
                .uri(AMADEUS_URL + "v2/shopping/flight-offers")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + this.amadeusTokenService.generateAccessToken().getAccessToken())
                .body(flightRequest)
                .retrieve()
                .toEntity(FlightResponseDTO.class)
                .getBody();

        if (response == null || response.getMeta() == null || response.getMeta().getCount() == 0) {
            throw new NoFlightsAvailableException(
                "No flights available for the selected dates",
                planCreateDTO.getOrigin(),
                planCreateDTO.getDestination(),
                planCreateDTO.getStartDate().toString(),
                planCreateDTO.getEndDate().toString()
            );
        }

        return response;
    }

    @Override
    public HotelOfferResponseDTO getHotelOffers(PlanCreateDTO planCreateDTO) {
        HotelResponseDTO hotelResponse = this.getHotels(planCreateDTO.getDestination());

        if (hotelResponse == null || hotelResponse.getData() == null || hotelResponse.getData().isEmpty()) {
            throw new NoHotelOffersAvailableException(
                "No hotels available for the selected destination",
                planCreateDTO.getDestination(),
                planCreateDTO.getStartDate().toString(),
                planCreateDTO.getEndDate().toString()
            );
        }

        List<String> hotelIds = hotelResponse.getData()
                .stream()
                .map(HotelDTO::getHotelId)
                .toList();

        String hotelOffersUri = String.format("/v3/shopping/hotel-offers?hotelIds=%s&checkInDate=%s&checkOutDate=%s&adults=%d&children=%d",
                String.join(",", hotelIds),
                planCreateDTO.getStartDate(),
                planCreateDTO.getEndDate(),
                planCreateDTO.getAdults(),
                planCreateDTO.getChildren()
        );

        HotelOfferResponseDTO response = this.restClient
                .get()
                .uri(AMADEUS_URL + hotelOffersUri)
                .header("Authorization", "Bearer " + this.amadeusTokenService.generateAccessToken().getAccessToken())
                .retrieve()
                .toEntity(HotelOfferResponseDTO.class)
                .getBody();

        if (response == null || response.getData() == null || response.getData().isEmpty()) {
            throw new NoHotelOffersAvailableException(
                "No hotel offers available for the selected dates",
                planCreateDTO.getDestination(),
                planCreateDTO.getStartDate().toString(),
                planCreateDTO.getEndDate().toString()
            );
        }

        return response;
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

    private HotelResponseDTO getHotels(String destination) {
        String hotelSearchUri = String.format("v1/reference-data/locations/hotels/by-city?cityCode=%s", destination);
        return this.restClient
                .get()
                .uri(AMADEUS_URL + hotelSearchUri)
                .header("Authorization", "Bearer " + this.amadeusTokenService.generateAccessToken().getAccessToken())
                .retrieve()
                .toEntity(HotelResponseDTO.class)
                .getBody();
    }
}
