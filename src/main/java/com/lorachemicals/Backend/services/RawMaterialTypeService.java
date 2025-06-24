package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.RawMaterialTypeRequestDTO;
import com.lorachemicals.Backend.model.RawMaterialType;
import com.lorachemicals.Backend.repository.RawMaterialTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RawMaterialTypeService {

    private final RawMaterialTypeRepository rawMaterialTypeRepo;

    public RawMaterialTypeService(RawMaterialTypeRepository rawMaterialTypeRepo) {
        this.rawMaterialTypeRepo = rawMaterialTypeRepo;
    }

    // Add new raw material type
    public RawMaterialType addRawMaterialType(RawMaterialType rawMaterialType) {
        return rawMaterialTypeRepo.save(rawMaterialType);
    }

    public List<RawMaterialType> getAllRawMaterials() {
        return rawMaterialTypeRepo.findAll();
    }

    public RawMaterialType updateRawMaterialType(Long id, RawMaterialTypeRequestDTO updateDTO) {
        return rawMaterialTypeRepo.findById(id).map(existing -> {
            existing.setName(updateDTO.getName());
            existing.setDescription(updateDTO.getDescription());
            existing.setCategory(updateDTO.getCategory());
            existing.setStatus(updateDTO.getStatus());
            return rawMaterialTypeRepo.save(existing);
        }).orElseThrow(() -> new RuntimeException("Raw Material Type not found with id: " + id));
    }

    public boolean deleteRawMaterialType(Long id) {
        if (rawMaterialTypeRepo.existsById(id)) {
            rawMaterialTypeRepo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
