package com.aitrip.database.repository;

import com.aitrip.database.model.Prompt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromptRepository extends JpaRepository<Prompt, Long> {
    Optional<Prompt> findByPlanName(String planName);
}
