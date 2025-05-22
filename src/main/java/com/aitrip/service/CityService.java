package com.aitrip.service;

import com.aitrip.database.dto.location.CityPageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CityService {
     Page<CityPageDTO> findCitiesByCountryAndQuery(String countryCode, String query, Pageable pageable);
}
