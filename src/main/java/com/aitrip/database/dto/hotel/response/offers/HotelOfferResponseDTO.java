package com.aitrip.database.dto.hotel.response.offers;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HotelOfferResponseDTO {
    private List<HotelOfferDataDTO> data;
}

