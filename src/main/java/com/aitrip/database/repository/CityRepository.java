package com.aitrip.database.repository;


import com.aitrip.database.dto.location.CityPageDTO;
import com.aitrip.database.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    @Query("""
            SELECT new com.aitrip.database.dto.location.CityPageDTO(c.name, c.iataCode)
            FROM City c
            WHERE LOWER(c.country.code) = LOWER(:countryCode) AND (
                  LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%'))
               OR LOWER(c.iataCode) LIKE LOWER(CONCAT('%', :query, '%'))
            )
            ORDER BY
                CASE
                    WHEN LOWER(c.name) = LOWER(:query) THEN 0
                    WHEN LOWER(c.name) LIKE LOWER(CONCAT(:query, '%')) THEN 1
                    WHEN LOWER(c.iataCode) = LOWER(:query) THEN 2
                    WHEN LOWER(c.iataCode) LIKE LOWER(CONCAT(:query, '%')) THEN 3
                    ELSE 4
                END,
                c.name ASC
            """)
    Page<CityPageDTO> findByCountryAndQuery(@Param("countryCode") String countryCode, @Param("query") String query, Pageable pageable);
}
