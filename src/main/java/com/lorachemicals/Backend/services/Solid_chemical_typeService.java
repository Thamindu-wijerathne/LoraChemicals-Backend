package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.Solid_chemical_typeRequestDTO;
import com.lorachemicals.Backend.dto.Solid_chemical_typeResponseDTO;
import com.lorachemicals.Backend.model.Solid_chemical_type;
import com.lorachemicals.Backend.repository.Solid_chemical_typeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Solid_chemical_typeService {

    @Autowired
    private Solid_chemical_typeRepository solidRepo;

    // Get all solid chemical types
    public List<Solid_chemical_typeResponseDTO> getAll() {
        try {
            return solidRepo.findAll()
                    .stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve solid chemical types: " + e.getMessage());
        }
    }

    // Delete a solid chemical type
    public void delete(Long id) {
        try {
            if (!solidRepo.existsById(id)) {
                throw new RuntimeException("Solid chemical type not found with id: " + id);
            }
            solidRepo.deleteById(id);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    // Get by ID
    public Solid_chemical_typeResponseDTO getById(Long id) {
        return solidRepo.findById(id)
                .map(this::convertToResponseDTO)
                .orElse(null);
    }

    // Create a solid chemical type
    public Solid_chemical_typeResponseDTO create(Solid_chemical_typeRequestDTO reqDTO) {
        try {
            Solid_chemical_type solid = new Solid_chemical_type();
            solid.setMelting_point(reqDTO.getMelting_point());

            Solid_chemical_type saved = solidRepo.save(solid);
            return convertToResponseDTO(saved);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create solid chemical type: " + e.getMessage());
        }
    }

    // Update an existing solid chemical type
    public Solid_chemical_typeResponseDTO update(Long id, Solid_chemical_typeRequestDTO reqDTO) {
        try {
            Solid_chemical_type solid = solidRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Solid chemical type not found with id: " + id));

            solid.setMelting_point(reqDTO.getMelting_point());

            Solid_chemical_type updated = solidRepo.save(solid);
            return convertToResponseDTO(updated);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update solid chemical type: " + e.getMessage());
        }
    }

    // DTO converter
    private Solid_chemical_typeResponseDTO convertToResponseDTO(Solid_chemical_type solid) {
        Solid_chemical_typeResponseDTO dto = new Solid_chemical_typeResponseDTO();
        dto.setId(solid.getSchemid());
        dto.setMelting_point(solid.getMelting_point());
        return dto;
    }
}