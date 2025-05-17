package com.aitrip.database.dto.hotel.response;

import com.aitrip.database.dto.hotel.response.data.HotelDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HotelResponseDTO {
    private List<HotelDTO> data;
}
