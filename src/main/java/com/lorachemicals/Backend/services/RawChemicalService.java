package com.lorachemicals.Backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lorachemicals.Backend.dto.RawChemicalRequestDTO;
import com.lorachemicals.Backend.model.RawChemical;
import com.lorachemicals.Backend.model.RawChemicalType;
import com.lorachemicals.Backend.repository.RawChemicalRepository;
import com.lorachemicals.Backend.repository.RawChemicalTypeRepository;

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

    // Get raw chemical by inventory ID
    public Optional<RawChemical> getRawChemicalById(Long inventoryId) {
        try {
            return rawChemicalRepository.findById(inventoryId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch raw chemical by ID: " + e.getMessage(), e);
        }
    }

    public RawChemical updateVolume(Long inventoryId, double volume) {
        try {
            RawChemical raw = rawChemicalRepository.findById(inventoryId)
                    .orElseThrow(() -> new RuntimeException("Raw chemical not found"));

            raw.setVolume(volume);
            return rawChemicalRepository.save(raw);
        } catch (Exception e) {
            throw new RuntimeException("Error updating volume: " + e.getMessage());
        }
    }

    public RawChemical updateLocation(Long inventoryId, String location) {
        try {
            RawChemical raw = rawChemicalRepository.findById(inventoryId)
                    .orElseThrow(() -> new RuntimeException("Raw chemical not found"));

            raw.setLocation(location);
            return rawChemicalRepository.save(raw);
        } catch (Exception e) {
            throw new RuntimeException("Error updating volume: " + e.getMessage());
        }
    }

    // Create new raw chemical
    public RawChemical createRawChemical(RawChemicalRequestDTO dto) {
        try {
            RawChemicalType chemicalType = rawChemicalTypeRepository.findById(dto.getChemid())
                    .orElseThrow(() -> new RuntimeException("Chemical type not found"));

            RawChemical chemical = new RawChemical();
            chemical.setChemicalType(chemicalType);
            chemical.setVolume(dto.getVolume());
            chemical.setLocation(dto.getLocation());

            return rawChemicalRepository.save(chemical);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create raw chemical: " + e.getMessage(), e);
        }
    }

    // Update existing raw chemical
    public RawChemical updateRawChemical(Long inventoryId, RawChemicalRequestDTO dto) {
        try {
            RawChemical chemical = rawChemicalRepository.findById(inventoryId)
                    .orElseThrow(() -> new RuntimeException("Raw chemical not found"));

            RawChemicalType chemicalType = rawChemicalTypeRepository.findById(dto.getChemid())
                    .orElseThrow(() -> new RuntimeException("Chemical type not found"));

            chemical.setChemicalType(chemicalType);
            chemical.setVolume(dto.getVolume());
            chemical.setLocation(dto.getLocation());

            return rawChemicalRepository.save(chemical);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update raw chemical: " + e.getMessage(), e);
        }
    }

    // Delete raw chemical by inventory ID
    public void deleteRawChemical(Long inventoryId) {
        try {
            rawChemicalRepository.deleteById(inventoryId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete raw chemical: " + e.getMessage(), e);
        }
    }

    // Get raw chemicals by chemical type ID
    public List<RawChemical> getRawChemicalsByChemicalTypeId(Long chemid) {
        try {
            // Find the chemical type first
            RawChemicalType chemicalType = rawChemicalTypeRepository.findById(chemid)
                    .orElseThrow(() -> new RuntimeException("Chemical type not found"));
            return rawChemicalRepository.findByChemicalType(chemicalType);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch raw chemicals by type: " + e.getMessage(), e);
        }
    }
}
