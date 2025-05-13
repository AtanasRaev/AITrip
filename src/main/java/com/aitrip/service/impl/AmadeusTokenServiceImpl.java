package com.aitrip.service.impl;

import com.aitrip.config.AmadeusConfig;
import com.aitrip.database.dto.TokenResponseDTO;
import com.aitrip.exception.external.amadeus.AmadeusTokenNotFoundException;
import com.aitrip.service.AmadeusTokenService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Service
public class AmadeusTokenServiceImpl implements AmadeusTokenService {
    private final AmadeusConfig amadeusConfig;
    private final RestClient restClient;

    public AmadeusTokenServiceImpl(AmadeusConfig amadeusConfig,
                                   RestClient restClient) {
        this.amadeusConfig = amadeusConfig;
        this.restClient = restClient;
    }

    @Cacheable("amadeusToken")
    public TokenResponseDTO generateAccessToken() {
        MultiValueMap<String,String> form = getFormData();
        TokenResponseDTO dto = restClient.post()
                .uri("https://test.api.amadeus.com/v1/security/oauth2/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(form)
                .retrieve()
                .toEntity(TokenResponseDTO.class)
                .getBody();
        if (dto == null) throw new AmadeusTokenNotFoundException("Failed to retrieve Amadeus access token");
        return dto;
    }

    private MultiValueMap<String, String> getFormData() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "client_credentials");
        formData.add("client_id", this.amadeusConfig.getApiKey());
        formData.add("client_secret", this.amadeusConfig.getApiSecret());
        return formData;
    }
}
