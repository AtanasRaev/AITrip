package com.aitrip.service.impl;

import com.aitrip.config.AmadeusConfig;
import com.aitrip.database.dto.TokenResponseDTO;
import com.aitrip.service.AmadeusService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Service
public class AmadeusServiceImpl implements AmadeusService {
    private final RestClient restClient;
    private final AmadeusConfig amadeusConfig;

    public AmadeusServiceImpl(RestClient restClient,
                              AmadeusConfig amadeusConfig) {
        this.restClient = restClient;
        this.amadeusConfig = amadeusConfig;
    }

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

    private MultiValueMap<String, String> getFormData() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "client_credentials");
        formData.add("client_id", this.amadeusConfig.getApiKey());
        formData.add("client_secret", this.amadeusConfig.getApiSecret());
        return formData;
    }
}
