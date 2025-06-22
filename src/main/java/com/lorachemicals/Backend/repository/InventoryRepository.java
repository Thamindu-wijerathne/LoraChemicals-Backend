package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository <Inventory,Long>{
}
