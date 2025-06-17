package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.WarehouseManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseManagerRepository extends JpaRepository<WarehouseManager, Long> {

    // Find warehouse manager by user ID
    WarehouseManager findByUserId(Long userId);
}
