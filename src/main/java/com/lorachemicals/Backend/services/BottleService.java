package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.BottleRequestDTO;
import com.lorachemicals.Backend.dto.BottleResponseDTO;
import com.lorachemicals.Backend.model.Bottle;
import com.lorachemicals.Backend.model.Labeltype;
import com.lorachemicals.Backend.model.RawMaterialType;
import com.lorachemicals.Backend.repository.BottleRepository;
import com.lorachemicals.Backend.repository.LabeltypeRepository;
import com.lorachemicals.Backend.repository.RawMaterialTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BottleService {

    @Autowired
    private BottleRepository bottleRepository;

    @Autowired
    private LabeltypeRepository labeltypeRepository;

    @Autowired
    private RawMaterialTypeRepository rawMaterialTypeRepository;

    // Create
    public BottleResponseDTO createBottle(BottleRequestDTO bottleRequestDTO) {
        try {
            Labeltype labeltype = labeltypeRepository.findById(bottleRequestDTO.getLabelTypeId())
                    .orElseThrow(() -> new RuntimeException("LabelType not found"));

            RawMaterialType rawMaterialType = null;
            if (bottleRequestDTO.getRawMaterialTypeId() != null) {
                rawMaterialType = rawMaterialTypeRepository.findById(bottleRequestDTO.getRawMaterialTypeId())
                        .orElseThrow(() -> new RuntimeException("RawMaterialType not found"));
            }

            Bottle bottle = new Bottle();
            bottle.setBottleid(bottleRequestDTO.getBottleid()); // Optional if auto-generated
            bottle.setLabelType(labeltype);
            bottle.setRawMaterialType(rawMaterialType);
            bottle.setQuantity(bottleRequestDTO.getQuantity());

            Bottle savedBottle = bottleRepository.save(bottle);
            return convertToResponseDTO(savedBottle);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create bottle: " + e.getMessage());
        }
    }

    private BottleResponseDTO convertToResponseDTO(Bottle bottle) {
        BottleResponseDTO dto = new BottleResponseDTO();
        dto.setBottleid(bottle.getBottleid());

        if (bottle.getLabelType() != null) {
            dto.setLabelTypeId(bottle.getLabelType().getlabelid());
            dto.setLabelTypeName(bottle.getLabelType().getName()); // adjust this field based on your entity
        }

        if (bottle.getRawMaterialType() != null) {
            dto.setRawMaterialTypeId(bottle.getRawMaterialType().getId());
            dto.setRawMaterialTypeName(bottle.getRawMaterialType().getName()); // adjust this field
        }

        dto.setQuantity(bottle.getQuantity());
        return dto;
    }
}
