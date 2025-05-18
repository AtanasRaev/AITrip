package com.aitrip.database.dto.hotel.response.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class HotelDTO {
    private String chainCode;

    private String iataCode;

    private Long dupeId;

    private String name;

    private String hotelId;
    
    private GeoCode geoCode;

    private Address address;

    @JsonFormat(
            pattern = "yyyy-MM-dd['T'HH:mm:ssXXX]",
            timezone = "UTC"
    )
    private OffsetDateTime lastUpdate;

    
    @Getter
    @Setter
    private static class GeoCode {
        private Double latitude;
        private Double longitude;
    }

    @Getter
    @Setter
    private static class Address {
        private String countryCode;
    }
}