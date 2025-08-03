package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.model.RawChemicalType;
import com.lorachemicals.Backend.repository.RawChemicalTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RawChemicalTypeService {

    @Autowired
    private RawChemicalTypeRepository rawChemicalTypeRepository;

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