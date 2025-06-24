package com.lorachemicals.Backend.services;

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

}
