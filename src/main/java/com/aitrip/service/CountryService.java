package com.aitrip.service;

import com.aitrip.database.dto.location.CountryPageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CountryService {
    Page<CountryPageDTO> findCountriesByQuery(String query, Pageable pageable);
}
