package com.aitrip.service.impl;

import com.aitrip.database.dto.location.CountryPageDTO;
import com.aitrip.database.repository.CountryRepository;
import com.aitrip.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    @Override
    public Page<CountryPageDTO> findCountriesByQuery(String query, Pageable pageable) {
        return this.countryRepository.findByQuery(query, pageable);
    }
}
