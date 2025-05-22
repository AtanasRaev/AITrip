package com.aitrip.database.repository;

import com.aitrip.database.dto.location.CountryPageDTO;
import com.aitrip.database.model.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    @Query("""
                SELECT new com.aitrip.database.dto.location.CountryPageDTO(c.code, c.name)
                FROM Country c
                WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%'))
                   OR LOWER(c.code) LIKE LOWER(CONCAT('%', :query, '%'))
                ORDER BY
                    CASE
                        WHEN LOWER(c.name) = LOWER(:query) THEN 0
                        WHEN LOWER(c.name) LIKE LOWER(CONCAT(:query, '%')) THEN 1
                        WHEN LOWER(c.code) = LOWER(:query) THEN 2
                        WHEN LOWER(c.code) LIKE LOWER(CONCAT(:query, '%')) THEN 3
                        ELSE 4
                    END,
                    c.name ASC
            """)
    Page<CountryPageDTO> findByQuery(@Param("query") String query, Pageable pageable);
}
