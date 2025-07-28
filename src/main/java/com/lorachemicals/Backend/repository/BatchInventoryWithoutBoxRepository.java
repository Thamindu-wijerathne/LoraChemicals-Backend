package com.lorachemicals.Backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lorachemicals.Backend.model.BatchInventoryWithoutBox;
import com.lorachemicals.Backend.model.BatchTypeWithoutBox;

public interface BatchInventoryWithoutBoxRepository extends JpaRepository<BatchInventoryWithoutBox, Long> {

    List<BatchInventoryWithoutBox> findByBatchTypeWithoutBoxIn(List<BatchTypeWithoutBox> batchTypes);
}
