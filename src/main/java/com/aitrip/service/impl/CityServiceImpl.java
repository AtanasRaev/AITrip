package com.aitrip.service.impl;

import com.aitrip.database.dto.location.CityPageDTO;
import com.aitrip.database.repository.CityRepository;
import com.aitrip.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;


    @Override
    public Page<CityPageDTO> findCitiesByCountryAndQuery(String countryCode, String query, Pageable pageable) {
        return this.cityRepository.findByCountryAndQuery(countryCode, query, pageable);
    }
}
