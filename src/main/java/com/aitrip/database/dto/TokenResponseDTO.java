package com.aitrip.database.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponseDTO {
    @JsonProperty("access_token")
    String accessToken;
}
