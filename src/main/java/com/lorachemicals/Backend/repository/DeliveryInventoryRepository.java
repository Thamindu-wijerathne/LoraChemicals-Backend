package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.DeliveryInventory;
import com.lorachemicals.Backend.model.DeliveryInventoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DeliveryInventoryRepository extends JpaRepository<DeliveryInventory, DeliveryInventoryId> {

    // Example: Get all items for a specific delivery ID
    List<DeliveryInventory> findByDelivery_Deliveryid(Long deliveryid);
}
