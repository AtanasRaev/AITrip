package com.aitrip.service.impl;

import com.aitrip.database.dto.PlanCreateDTO;
import com.aitrip.database.dto.flight.request.FlightRequestDTO;
import com.aitrip.database.dto.flight.response.FlightResponseDTO;
import com.aitrip.service.AmadeusService;
import com.aitrip.service.AmadeusTokenService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class AmadeusServiceImpl implements AmadeusService {
    private final RestClient restClient;
    private final AmadeusTokenService amadeusTokenService;

    public AmadeusServiceImpl(RestClient restClient,
                              AmadeusTokenService amadeusTokenService) {
        this.restClient = restClient;
        this.amadeusTokenService = amadeusTokenService;
    }

    private FlightResponseDTO getFlights(PlanCreateDTO planCreateDTO) {
        FlightRequestDTO flightRequest = createFlightRequest(planCreateDTO);

        return this.restClient
                .post()
                .uri("https://test.api.amadeus.com/v2/shopping/flight-offers")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + this.amadeusTokenService.generateAccessToken().getAccessToken())
                .body(flightRequest)
                .retrieve()
                .toEntity(FlightResponseDTO.class).getBody();
    }

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
}
