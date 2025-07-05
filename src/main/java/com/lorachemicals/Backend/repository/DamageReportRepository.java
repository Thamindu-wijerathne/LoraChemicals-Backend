package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.DamageReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DamageReportRepository extends JpaRepository<DamageReport, Long> {
    // Custom queries if needed
}
