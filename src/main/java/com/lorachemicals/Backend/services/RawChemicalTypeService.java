package com.lorachemicals.Backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lorachemicals.Backend.model.RawChemical;
import com.lorachemicals.Backend.model.RawChemicalType;
import com.lorachemicals.Backend.repository.RawChemicalTypeRepository;

@Service
public class RawChemicalTypeService {

    @Autowired
    private RawChemicalTypeRepository rawChemicalTypeRepository;

    @Autowired
    private RawChemicalService rawChemicalService;

    // Get all types
    public List<RawChemicalType> getAllChemicalTypes() {
        return rawChemicalTypeRepository.findAll();
    }

    // Get by ID
    public Optional<RawChemicalType> getChemicalTypeById(Long chemid) {
        return rawChemicalTypeRepository.findById(chemid);
    }

    // Add new type
    public RawChemicalType createChemicalType(RawChemicalType chemicalType) {
        return rawChemicalTypeRepository.save(chemicalType);
    }

    // Delete
    public void deleteChemicalType(Long chemid) {
        rawChemicalTypeRepository.deleteById(chemid);
    }

    // Delete with inventory check and cleanup
    public void deleteChemicalTypeWithInventoryCheck(Long chemid) {
        try {
            // Get all raw chemicals for this chemical type
            List<RawChemical> chemicals = rawChemicalService.getRawChemicalsByChemicalTypeId(chemid);
            
            // Remove chemicals with zero or negative volume
            chemicals.stream()
                    .filter(chemical -> chemical.getVolume() <= 0)
                    .forEach(chemical -> rawChemicalService.deleteRawChemical(chemical.getInventoryid()));
            
            // Re-check after cleanup
            chemicals = rawChemicalService.getRawChemicalsByChemicalTypeId(chemid);
            
            // If still has chemicals with volume > 0, throw exception
            if (!chemicals.isEmpty() && chemicals.stream().anyMatch(chemical -> chemical.getVolume() > 0)) {
                double totalVolume = chemicals.stream()
                        .mapToDouble(RawChemical::getVolume)
                        .sum();
                throw new RuntimeException("Cannot delete chemical type. It has " + totalVolume + " units in inventory.");
            }
            
            // Safe to delete chemical type
            rawChemicalTypeRepository.deleteById(chemid);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete chemical type: " + e.getMessage(), e);
        }
    }

    // Check inventory status for a chemical type
    public InventoryStatus checkInventoryStatus(Long chemid) {
        try {
            List<RawChemical> chemicals = rawChemicalService.getRawChemicalsByChemicalTypeId(chemid);
            
            // Remove chemicals with zero or negative volume first
            chemicals.stream()
                    .filter(chemical -> chemical.getVolume() <= 0)
                    .forEach(chemical -> rawChemicalService.deleteRawChemical(chemical.getInventoryid()));
            
            // Re-check after cleanup
            chemicals = rawChemicalService.getRawChemicalsByChemicalTypeId(chemid);
            
            double totalVolume = chemicals.stream()
                    .mapToDouble(RawChemical::getVolume)
                    .sum();
            
            boolean hasInventory = totalVolume > 0;
            
            return new InventoryStatus(hasInventory, totalVolume);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to check inventory status: " + e.getMessage(), e);
        }
    }

    // Inner class for inventory status
    public static class InventoryStatus {
        private boolean hasInventory;
        private double totalQuantity;

        public InventoryStatus(boolean hasInventory, double totalQuantity) {
            this.hasInventory = hasInventory;
            this.totalQuantity = totalQuantity;
        }

        public boolean isHasInventory() {
            return hasInventory;
        }

        public double getTotalQuantity() {
            return totalQuantity;
        }
    }

    // Update
    public RawChemicalType updateChemicalType(Long chemid, RawChemicalType updatedType) {
        RawChemicalType existing = rawChemicalTypeRepository.findById(chemid)
                .orElseThrow(() -> new RuntimeException("Chemical type not found"));
        existing.setName(updatedType.getName());
        existing.setDescription(updatedType.getDescription());
        existing.setType(updatedType.getType());
        return rawChemicalTypeRepository.save(existing);
    }
}