package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.model.RawChemical;
import com.lorachemicals.Backend.model.RawChemicalType;
import com.lorachemicals.Backend.repository.RawChemicalRepository;
import com.lorachemicals.Backend.repository.RawChemicalTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RawChemicalService {

    @Autowired
    private RawChemicalRepository rawChemicalRepository;

    @Autowired
    private RawChemicalTypeRepository rawChemicalTypeRepository;

    // Get all raw chemicals
    public List<RawChemical> getAllRawChemicals() {
        try {
            return rawChemicalRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch raw chemicals: " + e.getMessage(), e);
        }
    }

    // Get by inventory ID
    public Optional<RawChemical> getRawChemicalById(Long inventoryid) {
        try {
            return rawChemicalRepository.findById(inventoryid);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch raw chemical by ID: " + e.getMessage(), e);
        }
    }

    // Create new raw chemical
    public RawChemical createRawChemical(Long chemid, Double volume) {
        try {
            RawChemicalType type = rawChemicalTypeRepository.findById(chemid)
                    .orElseThrow(() -> new RuntimeException("Chemical type not found"));

            RawChemical chemical = new RawChemical();
            chemical.setChemicalType(type);
            chemical.setVolume(volume);
            return rawChemicalRepository.save(chemical);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create raw chemical: " + e.getMessage(), e);
        }
    }

    // Delete
    public void deleteRawChemical(Long inventoryid) {
        try {
            rawChemicalRepository.deleteById(inventoryid);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete raw chemical: " + e.getMessage(), e);
        }
    }

    // Sum of volumes grouped by chemical type
    public List<Object[]> getTotalVolumeGroupedByChemid() {
        try {
            return rawChemicalRepository.sumVolumeGroupedByChemid();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get volume sums: " + e.getMessage(), e);
        }
    }
}
