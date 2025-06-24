package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.Liquid_chemical_typeRequestDTO;
import com.lorachemicals.Backend.dto.Liquid_chemical_typeResponseDTO;
import com.lorachemicals.Backend.model.Liquid_chemical_type;
import com.lorachemicals.Backend.repository.Liquid_chemical_typeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Liquid_chemical_typeService {

    @Autowired
    private Liquid_chemical_typeRepository liquidRepo;

    // Get all liquid chemical types
    public List<Liquid_chemical_typeResponseDTO> getAll() {
        try {
            return liquidRepo.findAll()
                    .stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve liquid chemical types: " + e.getMessage());
        }
    }

    // Delete a liquid chemical type
    public void delete(Long id) {
        try {
            if (!liquidRepo.existsById(id)) {
                throw new RuntimeException("Liquid chemical type not found with id: " + id);
            }
            liquidRepo.deleteById(id);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    // Get by ID
    public Liquid_chemical_typeResponseDTO getById(Long id) {
        return liquidRepo.findById(id)
                .map(this::convertToResponseDTO)
                .orElse(null);
    }

    // Create a liquid chemical type
    public Liquid_chemical_typeResponseDTO create(Liquid_chemical_typeRequestDTO reqDTO) {
        try {
            Liquid_chemical_type liquid = new Liquid_chemical_type();
            liquid.setPh_level(reqDTO.getPh_level());

            Liquid_chemical_type saved = liquidRepo.save(liquid);
            return convertToResponseDTO(saved);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create liquid chemical type: " + e.getMessage());
        }
    }

    // Update an existing liquid chemical type
    public Liquid_chemical_typeResponseDTO update(Long id, Liquid_chemical_typeRequestDTO reqDTO) {
        try {
            Liquid_chemical_type liquid = liquidRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Liquid chemical type not found with id: " + id));

            liquid.setPh_level(reqDTO.getPh_level());

            Liquid_chemical_type updated = liquidRepo.save(liquid);
            return convertToResponseDTO(updated);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update liquid chemical type: " + e.getMessage());
        }
    }

    // DTO converter
    private Liquid_chemical_typeResponseDTO convertToResponseDTO(Liquid_chemical_type liquid) {
        Liquid_chemical_typeResponseDTO dto = new Liquid_chemical_typeResponseDTO();
        dto.setId(liquid.getLchemid());
        dto.setPh_level(liquid.getPh_level());
        return dto;
    }
}