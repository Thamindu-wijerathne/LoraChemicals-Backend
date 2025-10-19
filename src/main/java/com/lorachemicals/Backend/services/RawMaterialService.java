package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.model.RawMaterial;
import com.lorachemicals.Backend.repository.RawMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RawMaterialService {

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    public RawMaterial saveRawMaterial(RawMaterial rawMaterial) {
        try {
            return rawMaterialRepository.save(rawMaterial);
        } catch (Exception e) {
            System.err.println("Error saving RawMaterial: " + e.getMessage());
            return null;
        }
    }

    public List<RawMaterial> getAllRawMaterials() {
        try {
            return rawMaterialRepository.findAll();
        } catch (Exception e) {
            System.err.println("Error fetching RawMaterials: " + e.getMessage());
            return null;
        }
    }
    public List<RawMaterial> getAllLowStockRawMaterials() {
        try {
            List<RawMaterial> RawMaterialList  = rawMaterialRepository.findAll();
            return rawMaterialRepository.findAll();
        } catch (Exception e) {
            System.err.println("Error fetching RawMaterials: " + e.getMessage());
            return null;
        }
    }

    public Optional<RawMaterial> getRawMaterialById(Long id) {
        try {
            return rawMaterialRepository.findById(id);
        } catch (Exception e) {
            System.err.println("Error fetching RawMaterial by ID: " + e.getMessage());
            return Optional.empty();
        }
    }

    public boolean deleteRawMaterial(Long id) {
        try {
            rawMaterialRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting RawMaterial: " + e.getMessage());
            return false;
        }
    }
}
