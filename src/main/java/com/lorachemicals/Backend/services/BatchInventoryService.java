package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.model.BatchInventory;
import com.lorachemicals.Backend.model.BatchType;
import com.lorachemicals.Backend.repository.BatchInventoryRepository;
import com.lorachemicals.Backend.repository.BatchTypeRepository;
import com.lorachemicals.Backend.dto.BatchInventoryRequestDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BatchInventoryService {

    @Autowired
    private BatchInventoryRepository batchInventoryRepository;

    @Autowired
    private BatchTypeRepository batchTypeRepository;

    // Get all batch inventories
    public List<BatchInventory> getAllBatchInventories() {
        try {
            return batchInventoryRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch batch inventories: " + e.getMessage(), e);
        }
    }

    // Get batch inventory by inventory ID
    public Optional<BatchInventory> getBatchInventoryById(Long inventoryId) {
        try {
            return batchInventoryRepository.findById(inventoryId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch batch inventory by ID: " + e.getMessage(), e);
        }
    }

    // Create new batch inventory
    public BatchInventory createBatchInventory(BatchInventoryRequestDTO dto) {
        try {
            BatchType batchType = batchTypeRepository.findById(dto.getBatchtypeid())
                    .orElseThrow(() -> new RuntimeException("Batch type not found"));

            BatchInventory batchInventory = new BatchInventory();
            batchInventory.setBatchType(batchType);
            batchInventory.setBatch_quantity(dto.getBatch_quantity());
            batchInventory.setLocation(dto.getLocation());

            return batchInventoryRepository.save(batchInventory);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create batch inventory: " + e.getMessage(), e);
        }
    }

    // Update batch quantity
    public BatchInventory updateBatchQuantity(Long inventoryId, int batchQuantity) {
        try {
            BatchInventory batchInventory = batchInventoryRepository.findById(inventoryId)
                    .orElseThrow(() -> new RuntimeException("Batch inventory not found"));

            batchInventory.setBatch_quantity(batchQuantity);
            return batchInventoryRepository.save(batchInventory);
        } catch (Exception e) {
            throw new RuntimeException("Error updating batch quantity: " + e.getMessage());
        }
    }

    // Update location
    public BatchInventory updateLocation(Long inventoryId, String location) {
        try {
            BatchInventory batchInventory = batchInventoryRepository.findById(inventoryId)
                    .orElseThrow(() -> new RuntimeException("Batch inventory not found"));

            batchInventory.setLocation(location);
            return batchInventoryRepository.save(batchInventory);
        } catch (Exception e) {
            throw new RuntimeException("Error updating location: " + e.getMessage());
        }
    }

    // Update existing batch inventory
    public BatchInventory updateBatchInventory(Long inventoryId, BatchInventoryRequestDTO dto) {
        try {
            BatchInventory batchInventory = batchInventoryRepository.findById(inventoryId)
                    .orElseThrow(() -> new RuntimeException("Batch inventory not found"));

            BatchType batchType = batchTypeRepository.findById(dto.getBatchtypeid())
                    .orElseThrow(() -> new RuntimeException("Batch type not found"));

            batchInventory.setBatchType(batchType);
            batchInventory.setBatch_quantity(dto.getBatch_quantity());
            batchInventory.setLocation(dto.getLocation());

            return batchInventoryRepository.save(batchInventory);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update batch inventory: " + e.getMessage(), e);
        }
    }

    // Delete batch inventory by inventory ID
    public void deleteBatchInventory(Long inventoryId) {
        try {
            batchInventoryRepository.deleteById(inventoryId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete batch inventory: " + e.getMessage(), e);
        }
    }
}