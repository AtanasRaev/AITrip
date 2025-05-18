package com.aitrip.service;

import com.aitrip.config.AmadeusEnvironment;
import com.aitrip.database.dto.TokenResponseDTO;

public interface AmadeusTokenService {
    /**
     * Generates an access token for the specified Amadeus environment.
     *
     * @param environment the Amadeus environment
     * @return the token response
     */
    TokenResponseDTO generateAccessToken(AmadeusEnvironment environment);

}
