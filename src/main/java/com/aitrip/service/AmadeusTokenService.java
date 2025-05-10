package com.aitrip.service;

import com.aitrip.database.dto.TokenResponseDTO;

public interface AmadeusTokenService {
    TokenResponseDTO generateAccessToken();
}
