package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.BatchInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchInventoryRepository extends JpaRepository<BatchInventory, Long> {
}
