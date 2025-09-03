package com.lorachemicals.Backend.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lorachemicals.Backend.dto.BatchInventoryWithoutBoxRequestDTO;
import com.lorachemicals.Backend.model.BatchInventoryWithoutBox;
import com.lorachemicals.Backend.model.BatchTypeWithoutBox;
import com.lorachemicals.Backend.model.ParentBatchType;
import com.lorachemicals.Backend.model.ProductTypeVolume;
import com.lorachemicals.Backend.repository.BatchInventoryWithoutBoxRepository;
import com.lorachemicals.Backend.repository.BatchTypeWithoutBoxRepository;
import com.lorachemicals.Backend.repository.ProductTypeVolumeRepository;

@Service
public class BatchInventoryWithoutBoxService {

    @Autowired
    private BatchInventoryWithoutBoxRepository batchInventoryWithoutBoxRepository;

    @Autowired
    private ProductTypeVolumeRepository productTypeVolumeRepository;

    @Autowired
    private BatchTypeWithoutBoxRepository batchTypeWithoutBoxRepository;

    // Get all batch inventories without box
    public List<BatchInventoryWithoutBox> getAllBatchInventoriesWithoutBox() {
        try {
            return batchInventoryWithoutBoxRepository.findAllWithParentBatchType();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch batch inventories without box: " + e.getMessage(), e);
        }
    }

    public List<BatchInventoryWithoutBox> getLowStockBatchInventoriesWithoutBox() {
        try {
            int threshold  = 10;
            return batchInventoryWithoutBoxRepository.findLowStockWithParentBatchType(threshold)
                    .stream()
                    .limit(5) // get last 5
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch batch inventories without box: " + e.getMessage(), e);
        }
    }

    public BigDecimal getTotalValueOfInventory() {

    }

    // Get batch inventory without box by inventory ID
    public Optional<BatchInventoryWithoutBox> getBatchInventoryWithoutBoxById(Long inventoryId) {
        try {
            return batchInventoryWithoutBoxRepository.findById(inventoryId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch batch inventory without box by ID: " + e.getMessage(), e);
        }
    }

    // Get batch inventories without box by PTV ID
    public List<BatchInventoryWithoutBox> getBatchInventoriesWithoutBoxByPtvId(Long ptvid) {
        try {
            ProductTypeVolume ptv = productTypeVolumeRepository.findById(ptvid)
                    .orElseThrow(() -> new RuntimeException("ProductTypeVolume not found with id: " + ptvid));

            List<BatchTypeWithoutBox> batchTypes = batchTypeWithoutBoxRepository.findByProductTypeVolume(ptv);

            // Convert BatchTypeWithoutBox to ParentBatchType for the query
            List<ParentBatchType> parentBatchTypes = batchTypes.stream()
                    .map(bt -> (ParentBatchType) bt)
                    .collect(java.util.stream.Collectors.toList());

            return batchInventoryWithoutBoxRepository.findByParentBatchTypeIn(parentBatchTypes);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch batch inventories without box by PTV ID: " + e.getMessage(), e);
        }
    }

    // Create new batch inventory without box
    public BatchInventoryWithoutBox createBatchInventoryWithoutBox(BatchInventoryWithoutBoxRequestDTO dto) {
        try {
            BatchTypeWithoutBox batchTypeWithoutBox = batchTypeWithoutBoxRepository.findById(dto.getBatchtypewithoutboxid())
                    .orElseThrow(() -> new RuntimeException("Batch type without box not found"));

            BatchInventoryWithoutBox batchInventoryWithoutBox = new BatchInventoryWithoutBox();
            batchInventoryWithoutBox.setBatchTypeWithoutBox(batchTypeWithoutBox);
            batchInventoryWithoutBox.setBatch_quantity(dto.getBatch_quantity());
            batchInventoryWithoutBox.setLocation(dto.getLocation());

            return batchInventoryWithoutBoxRepository.save(batchInventoryWithoutBox);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create batch inventory without box: " + e.getMessage(), e);
        }
    }

    // Update batch quantity
    public BatchInventoryWithoutBox updateBatchQuantity(Long inventoryId, int batchQuantity) {
        try {
            BatchInventoryWithoutBox batchInventoryWithoutBox = batchInventoryWithoutBoxRepository.findById(inventoryId)
                    .orElseThrow(() -> new RuntimeException("Batch inventory without box not found"));

            batchInventoryWithoutBox.setBatch_quantity(batchQuantity);
            return batchInventoryWithoutBoxRepository.save(batchInventoryWithoutBox);
        } catch (Exception e) {
            throw new RuntimeException("Error updating batch quantity: " + e.getMessage());
        }
    }

    // Update location
    public BatchInventoryWithoutBox updateLocation(Long inventoryId, String location) {
        try {
            BatchInventoryWithoutBox batchInventoryWithoutBox = batchInventoryWithoutBoxRepository.findById(inventoryId)
                    .orElseThrow(() -> new RuntimeException("Batch inventory without box not found"));

            batchInventoryWithoutBox.setLocation(location);
            return batchInventoryWithoutBoxRepository.save(batchInventoryWithoutBox);
        } catch (Exception e) {
            throw new RuntimeException("Error updating location: " + e.getMessage());
        }
    }

    // Update existing batch inventory without box
    public BatchInventoryWithoutBox updateBatchInventoryWithoutBox(Long inventoryId, BatchInventoryWithoutBoxRequestDTO dto) {
        try {
            BatchInventoryWithoutBox batchInventoryWithoutBox = batchInventoryWithoutBoxRepository.findById(inventoryId)
                    .orElseThrow(() -> new RuntimeException("Batch inventory without box not found"));

            BatchTypeWithoutBox batchTypeWithoutBox = batchTypeWithoutBoxRepository.findById(dto.getBatchtypewithoutboxid())
                    .orElseThrow(() -> new RuntimeException("Batch type without box not found"));

            batchInventoryWithoutBox.setBatchTypeWithoutBox(batchTypeWithoutBox);
            batchInventoryWithoutBox.setBatch_quantity(dto.getBatch_quantity());
            batchInventoryWithoutBox.setLocation(dto.getLocation());

            return batchInventoryWithoutBoxRepository.save(batchInventoryWithoutBox);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update batch inventory without box: " + e.getMessage(), e);
        }
    }

    // Delete batch inventory without box by inventory ID
    public void deleteBatchInventoryWithoutBox(Long inventoryId) {
        try {
            batchInventoryWithoutBoxRepository.deleteById(inventoryId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete batch inventory without box: " + e.getMessage(), e);
        }
    }
}
