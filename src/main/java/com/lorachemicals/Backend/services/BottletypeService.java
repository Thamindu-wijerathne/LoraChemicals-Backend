package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.model.Bottletype;
import com.lorachemicals.Backend.repository.BottletypeRepository;
import com.lorachemicals.Backend.dto.BottletypeRequestDTO;
import com.lorachemicals.Backend.dto.BottletypeResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BottletypeService {

    @Autowired
    private BottletypeRepository bottleRepo;

    // Get all bottle types
    public List<BottletypeResponseDTO> getAll() {
        return bottleRepo.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get bottle type by ID
    public BottletypeResponseDTO getById(Long id) {
        return bottleRepo.findById(id)
                .map(this::convertToResponseDTO)
                .orElse(null);
    }

    // Create a new bottle type
    public BottletypeResponseDTO createBottletype(BottletypeRequestDTO reqDTO) {
        Bottletype newBottle = new Bottletype();
        newBottle.setName(reqDTO.getName());
        newBottle.setVolume(reqDTO.getVolume()); // Add this line
        // Set other fields here...

        Bottletype savedBottle = bottleRepo.save(newBottle);
        return convertToResponseDTO(savedBottle);
    }

    // Update an existing bottle type
    public BottletypeResponseDTO updateBottletype(Long id, BottletypeRequestDTO reqDTO) {
        Bottletype bottle = bottleRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Bottle type not found with id: " + id));

        bottle.setName(reqDTO.getName());
        bottle.setVolume(reqDTO.getVolume());
        // Set other fields here...

        Bottletype updatedBottle = bottleRepo.save(bottle);
        return convertToResponseDTO(updatedBottle);
    }

    // Delete a bottle type
    public void deleteBottletype(Long id) {
        if (!bottleRepo.existsById(id)) {
            throw new RuntimeException("Bottle type not found with id: " + id);
        }
        bottleRepo.deleteById(id);
    }

    // Convert entity to response DTO
    private BottletypeResponseDTO convertToResponseDTO(Bottletype bottle) {
        BottletypeResponseDTO dto = new BottletypeResponseDTO();
        dto.setBottleid(bottle.getBottleid());
        dto.setName(bottle.getName());
        dto.setVolume(bottle.getVolume());
        return dto;
    }
}