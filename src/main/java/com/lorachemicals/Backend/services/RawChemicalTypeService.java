package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.RawChemicalTypeRequestDTO;
import com.lorachemicals.Backend.dto.RawChemicalTypeResponseDTO;
import com.lorachemicals.Backend.model.RawChemicalType;
import com.lorachemicals.Backend.repository.RawChemicalTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RawChemicalTypeService {

    private final RawChemicalTypeRepository rawChemicalTypeRepository;

    @Autowired
    public RawChemicalTypeService(RawChemicalTypeRepository rawChemicalTypeRepository) {
        this.rawChemicalTypeRepository = rawChemicalTypeRepository;
    }

    // Create
    public RawChemicalTypeResponseDTO createRawChemicalType(RawChemicalTypeRequestDTO request) {
        RawChemicalType entity = new RawChemicalType();
        entity.setName(request.getName());
        entity.setType(request.getType());
        entity.setDescription(request.getDescription());
        entity = rawChemicalTypeRepository.save(entity);
        return toResponseDTO(entity);
    }

    // Read by ID
    public RawChemicalTypeResponseDTO getRawChemicalType(Long chemid) {
        RawChemicalType entity = rawChemicalTypeRepository.findById(chemid)
                .orElseThrow(() -> new RuntimeException("Raw chemical type with id " + chemid + " not found"));
        return toResponseDTO(entity);
    }

    // List all
    public List<RawChemicalTypeResponseDTO> getAllRawChemicalTypes() {
        return rawChemicalTypeRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Update
    public RawChemicalTypeResponseDTO updateRawChemicalType(Long chemid, RawChemicalTypeRequestDTO request) {
        RawChemicalType entity = rawChemicalTypeRepository.findById(chemid)
                .orElseThrow(() -> new RuntimeException("Raw chemical type with id " + chemid + " does not exist"));
        entity.setName(request.getName());
        entity.setType(request.getType());
        entity.setDescription(request.getDescription());
        entity = rawChemicalTypeRepository.save(entity);
        return toResponseDTO(entity);
    }

    // Delete
    public void deleteRawChemicalType(Long chemid) {
        if (!rawChemicalTypeRepository.existsById(chemid)) {
            throw new RuntimeException("Raw chemical type with id " + chemid + " not found");
        }
        rawChemicalTypeRepository.deleteById(chemid);
    }

    // Entity -> ResponseDTO
    private RawChemicalTypeResponseDTO toResponseDTO(RawChemicalType entity) {
        RawChemicalTypeResponseDTO dto = new RawChemicalTypeResponseDTO();
        dto.setChemid(entity.getChemid());
        dto.setName(entity.getName());
        dto.setType(entity.getType());
        dto.setDescription(entity.getDescription());
        return dto;
    }
}