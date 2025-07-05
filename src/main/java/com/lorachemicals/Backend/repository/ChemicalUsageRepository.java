package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.ChemicalUsage;
import com.lorachemicals.Backend.model.ChemicalUsageId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChemicalUsageRepository extends JpaRepository<ChemicalUsage, ChemicalUsageId> {
}
