package com.aitrip.web;

import com.aitrip.database.dto.location.CityPageDTO;
import com.aitrip.database.dto.location.CountryPageDTO;
import com.aitrip.service.CityService;
import com.aitrip.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class LocationController {
    private final CountryService countryService;
    private final CityService cityService;

    @GetMapping("/countries")
    public ResponseEntity<Map<String, Object>> getCountryByQuery(@RequestParam(value = "name") String query) {
        Pageable pageable = PageRequest.of(0, 10);
        Page<CountryPageDTO> countries = this.countryService.findCountriesByQuery(query, pageable);

        return ResponseEntity.ok(Map.of(
                "message", "Countries received successfully",
                "countries", countries.getContent()
        ));
    }

    @GetMapping("/cities")
    public ResponseEntity<Map<String, Object>> getCitiesByQuery(@RequestParam(value = "country") String countryCode,
                                                                @RequestParam(value = "name") String query) {
        Pageable pageable = PageRequest.of(0, 10);
        Page<CityPageDTO> cities = this.cityService.findCitiesByCountryAndQuery(countryCode, query, pageable);

        return ResponseEntity.ok(Map.of(
                "message", "Cities received successfully",
                "cities", cities.getContent()
        ));
    }
}
