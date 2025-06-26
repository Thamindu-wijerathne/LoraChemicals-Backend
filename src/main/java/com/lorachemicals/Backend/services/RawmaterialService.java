package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.RawmaterialRequestDTO;
import com.lorachemicals.Backend.dto.RawmaterialResponseDTO;
import com.lorachemicals.Backend.model.Inventory;
import com.lorachemicals.Backend.model.RawMaterialType;
import com.lorachemicals.Backend.model.Rawmaterial;
import com.lorachemicals.Backend.repository.InventoryRepository;
import com.lorachemicals.Backend.repository.RawMaterialTypeRepository;
import com.lorachemicals.Backend.repository.RawmaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RawmaterialService {

    @Autowired
    private RawmaterialRepository rawmaterialRepository;

    @Autowired
    private RawMaterialTypeRepository rawMaterialTypeRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<RawmaterialResponseDTO> getAll() {
        return rawmaterialRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public RawmaterialResponseDTO getById(Long id) {
        Rawmaterial rawmaterial = rawmaterialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rawmaterial not found with rmtid: " + id));
        return convertToResponseDTO(rawmaterial);
    }

    public void deleteRawmaterial(Long id) {
        if (!rawmaterialRepository.existsById(id)) {
            throw new RuntimeException("Rawmaterial not found with rmtid: " + id);
        }
        rawmaterialRepository.deleteById(id);
    }

    public RawmaterialResponseDTO createRawmaterial(RawmaterialRequestDTO reqDTO) {
        Rawmaterial rawmaterial = new Rawmaterial();

        // DON'T set rmtid manually when using @MapsId
        // rawmaterial.setRmtid(reqDTO.getRmtid()); // Remove this line

        // Set RawMaterialType FIRST - @MapsId will automatically set the rmtid
        RawMaterialType rawMaterialType = rawMaterialTypeRepository.findById(reqDTO.getRmtid())
                .orElseThrow(() -> new RuntimeException("RawMaterialType not found with id: " + reqDTO.getRmtid()));
        rawmaterial.setRawMaterialType(rawMaterialType);

        // Set Inventory (optional)
        if (reqDTO.getInventoryid() != null) {
            Inventory inventory = inventoryRepository.findById(reqDTO.getInventoryid())
                    .orElseThrow(() -> new RuntimeException("Inventory not found with id: " + reqDTO.getInventoryid()));
            rawmaterial.setInventory(inventory);
        } else {
            rawmaterial.setInventory(null);
        }

        Rawmaterial saved = rawmaterialRepository.save(rawmaterial);
        return convertToResponseDTO(saved);
    }

    public RawmaterialResponseDTO updateRawmaterial(Long id, RawmaterialRequestDTO reqDTO) {
        Rawmaterial rawmaterial = rawmaterialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rawmaterial not found with rmtid: " + id));

        // Update Inventory only (cannot update rmtid due to @MapsId)
        if (reqDTO.getInventoryid() != null) {
            Inventory inventory = inventoryRepository.findById(reqDTO.getInventoryid())
                    .orElseThrow(() -> new RuntimeException("Inventory not found with id: " + reqDTO.getInventoryid()));
            rawmaterial.setInventory(inventory);
        } else {
            rawmaterial.setInventory(null);
        }

        Rawmaterial updated = rawmaterialRepository.save(rawmaterial);
        return convertToResponseDTO(updated);
    }

    private RawmaterialResponseDTO convertToResponseDTO(Rawmaterial rawmaterial) {
        RawmaterialResponseDTO dto = new RawmaterialResponseDTO();
        dto.setRmtid(rawmaterial.getRmtid());

        if (rawmaterial.getRawMaterialType() != null) {
            dto.setRawMaterialTypeId(rawmaterial.getRawMaterialType().getId());
            dto.setRawMaterialTypeName(rawmaterial.getRawMaterialType().getName());
        }

        if (rawmaterial.getInventory() != null) {
            dto.setInventoryid(rawmaterial.getInventory().getId()); // Fixed: using getInventoryid() instead of getId()
            dto.setLocation(rawmaterial.getInventory().getLocation());
        }

        return dto;
    }
}