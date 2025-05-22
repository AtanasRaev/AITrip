package com.aitrip.utils;

import com.aitrip.database.model.City;
import com.aitrip.database.model.Country;
import com.aitrip.database.repository.CityRepository;
import com.aitrip.database.repository.CountryRepository;
import com.opencsv.CSVReader;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.util.*;

@Component
@Profile("!test")
@RequiredArgsConstructor // Lombok for constructor injection of repositories
public class Init implements CommandLineRunner {
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;

    @Override
    public void run(String... args) throws Exception {
        if (countryRepository.count() > 0 || cityRepository.count() > 0) {
            System.out.println("Database already initialized. Skipping.");
            return;
        }
        String countriesPath = "";
        String citiesPath = "";
        // Step 1: Parse countries
        Map<String, Country> countryMap = new HashMap<>();
        try (CSVReader reader = new CSVReader(new FileReader(countriesPath))) {
            String[] header = reader.readNext();
            int idxCode = Arrays.asList(header).indexOf("code");
            int idxName = Arrays.asList(header).indexOf("name");

            String[] row;
            while ((row = reader.readNext()) != null) {
                String code = row[idxCode];
                String name = row[idxName];
                if (code == null || code.isBlank() || name == null || name.isBlank()) continue;

                Country country = new Country();
                country.setCode(code);
                country.setName(name);
                countryRepository.save(country); // Save to DB
                countryMap.put(code, country);
            }
        }

        // Step 2: Parse cities (only those with valid IATA codes)
        Set<String> seen = new HashSet<>();
        List<City> cities = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(citiesPath))) {
            String[] header = reader.readNext();
            int idxMunicipality = Arrays.asList(header).indexOf("municipality");
            int idxCountry = Arrays.asList(header).indexOf("iso_country");
            int idxIata = Arrays.asList(header).indexOf("iata_code");
            int idxLat = Arrays.asList(header).indexOf("latitude_deg");
            int idxLon = Arrays.asList(header).indexOf("longitude_deg");

            String[] row;
            while ((row = reader.readNext()) != null) {
                String municipality = row[idxMunicipality];
                String countryCode = row[idxCountry];
                String iata = row[idxIata];
                String lat = row[idxLat];
                String lon = row[idxLon];

                if (municipality == null || municipality.isBlank() ||
                        countryCode == null || countryCode.isBlank() ||
                        iata == null || iata.isBlank())
                    continue; // skip cities without IATA

                String key = municipality.toLowerCase() + "," + countryCode.toUpperCase();
                if (seen.contains(key)) continue;
                seen.add(key);

                Country country = countryMap.get(countryCode);
                if (country == null) continue; // skip if country is missing

                City city = new City();
                city.setName(municipality);
                city.setIataCode(iata);
                city.setCountry(country);
                city.setLatitude(parseDoubleOrNull(lat));
                city.setLongitude(parseDoubleOrNull(lon));
                cities.add(city);
            }
        }

        cityRepository.saveAll(cities); // batch insert to DB

        System.out.println("Imported " + cities.size() + " cities with airports.");
        cities.stream().limit(10).forEach(c ->
                System.out.println(c.getName() + " (" + c.getCountry().getCode() + ") - " + c.getIataCode())
        );
    }

    private static Double parseDoubleOrNull(String value) {
        try {
            return (value == null || value.isBlank()) ? null : Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}

