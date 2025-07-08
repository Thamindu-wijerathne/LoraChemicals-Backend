package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.Production;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductionRepository extends JpaRepository<Production, Long> {
}
