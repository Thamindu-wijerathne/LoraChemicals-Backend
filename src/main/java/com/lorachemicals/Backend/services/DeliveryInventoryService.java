package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.model.DeliveryInventory;
import com.lorachemicals.Backend.model.DeliveryInventoryId;
import com.lorachemicals.Backend.repository.DeliveryInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class DeliveryInventoryService {

    @Autowired
    private DeliveryInventoryRepository deliveryInventoryRepository;

    // Get all DeliveryInventory records
    public List<DeliveryInventory> getAllDeliveryInventories() {
        try {
            return deliveryInventoryRepository.findAll();
        } catch (Exception e) {
            System.out.println("Error fetching all delivery inventories: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    // Get DeliveryInventory records by deliveryId
    public List<DeliveryInventory> getByDeliveryId(Long deliveryId) {
        try {
            return deliveryInventoryRepository.findByDelivery_Deliveryid(deliveryId);
        } catch (Exception e) {
            System.out.println("Error fetching delivery inventories by deliveryId: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    // Optional: Get one by composite key
    public DeliveryInventory getById(DeliveryInventoryId id) {
        try {
            return deliveryInventoryRepository.findById(id).orElse(null);
        } catch (Exception e) {
            System.out.println("Error fetching delivery inventory by ID: " + e.getMessage());
            return null;
        }
    }

}
