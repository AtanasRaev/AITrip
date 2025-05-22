package com.aitrip.database.dto.flight;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FlightRequestDTO {
    private List<DestinationDTO> originDestinations;

    private List<Traveler> travelers;

    private List<String> sources;

    public FlightRequestDTO() {
        this.originDestinations = new ArrayList<>();
        this.travelers = new ArrayList<>();
        this.sources = new ArrayList<>();
    }

    @Getter
    @Setter
    public static class DestinationDTO {
        private String id;
        private String originLocationCode;
        private String destinationLocationCode;
        private DepartureDateDTO departureDateTimeRange;

        public DestinationDTO(String id,
                              String originLocationCode,
                              String destinationLocationCode,
                              DepartureDateDTO departureDateTimeRange) {
            this.id = id;
            this.originLocationCode = originLocationCode;
            this.destinationLocationCode = destinationLocationCode;
            this.departureDateTimeRange = departureDateTimeRange;
        }
    }

    @Getter
    @Setter
    public static class Traveler {
        private String id;
        private String travelerType;

        public Traveler(String id, String travelerType) {
            this.id = id;
            this.travelerType = travelerType;
        }
    }

    @Getter
    @Setter
    public static class DepartureDateDTO {
        private LocalDate date;

        public DepartureDateDTO(LocalDate date) {
            this.date = date;
        }
    }
}
