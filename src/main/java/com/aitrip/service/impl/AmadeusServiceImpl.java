package com.aitrip.service.impl;

import com.aitrip.config.AmadeusConfig;
import com.aitrip.database.dto.flight.request.FlightRequestDTO;
import com.aitrip.database.dto.flight.response.FlightResponseDTO;
import com.aitrip.database.dto.PlanCreateDTO;
import com.aitrip.database.dto.TokenResponseDTO;
import com.aitrip.service.AmadeusService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class AmadeusServiceImpl implements AmadeusService {
    private final RestClient restClient;
    private final AmadeusConfig amadeusConfig;

    public AmadeusServiceImpl(RestClient restClient,
                              AmadeusConfig amadeusConfig) {
        this.restClient = restClient;
        this.amadeusConfig = amadeusConfig;
    }

    //TODO: cache this method, can you use the cache the in other method or does it start again
    private String generateAccessToken() {
        MultiValueMap<String, String> formData = getFormData();

        ResponseEntity<TokenResponseDTO> response = this.restClient
                .post()
                .uri("https://test.api.amadeus.com/v1/security/oauth2/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(formData)
                .retrieve()
                .toEntity(TokenResponseDTO.class);

        if (response.getBody() == null) {
            throw new RuntimeException("Failed to obtain access token from Amadeus API");
        }

        return response.getBody().getAccessToken();
    }

    private FlightResponseDTO getFlights(PlanCreateDTO planCreateDTO) {
        FlightRequestDTO flightRequest = createFlightRequest(planCreateDTO);

        return this.restClient
                .post()
                .uri("https://test.api.amadeus.com/v2/shopping/flight-offers")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + generateAccessToken())
                .body(flightRequest)
                .retrieve()
                .toEntity(FlightResponseDTO.class).getBody();
    }

    private MultiValueMap<String, String> getFormData() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "client_credentials");
        formData.add("client_id", this.amadeusConfig.getApiKey());
        formData.add("client_secret", this.amadeusConfig.getApiSecret());
        return formData;
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
