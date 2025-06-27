package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.model.Bottletype;
import com.lorachemicals.Backend.repository.BottletypeRepository;
import com.lorachemicals.Backend.dto.BottletypeRequestDTO;
import com.lorachemicals.Backend.dto.BottletypeResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BottletypeService {

    @Autowired
    private BottletypeRepository bottletypeRepository;

    // Get all bottle types
    public List<BottletypeResponseDTO> getAllBottletypes() {
        return bottletypeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get bottle type by ID
    public BottletypeResponseDTO getBottleTypeById(Long id) {
        try {
            Optional<Bottletype> optional = bottletypeRepository.findById(id);
            return optional.map(this::convertToDTO).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Create a new bottle type
    public BottletypeResponseDTO createBottleType(BottletypeRequestDTO dto) {
        try {
            Bottletype bottletype = new Bottletype();
            bottletype.setName(dto.getName());
            bottletype.setVolume(dto.getVolume());

            Bottletype saved = bottletypeRepository.save(bottletype);
            return convertToDTO(saved);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update an existing bottle type
    public BottletypeResponseDTO updateBottleType(Long id, BottletypeRequestDTO dto) {
        try {
            Optional<Bottletype> optional = bottletypeRepository.findById(id);
            if (optional.isPresent()) {
                Bottletype bottletype = optional.get();
                bottletype.setName(dto.getName());
                bottletype.setVolume(dto.getVolume());

                Bottletype updated = bottletypeRepository.save(bottletype);
                return convertToDTO(updated);
            } else {
                return null; // not found
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Delete a bottle type
    public boolean deleteBottleType(Long id) {
        try {
            if (bottletypeRepository.existsById(id)) {
                bottletypeRepository.deleteById(id);
                return true;
            } else {
                return false; // not found
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Convert entity to response DTO
    private BottletypeResponseDTO convertToDTO(Bottletype entity) {
        return new BottletypeResponseDTO(entity.getBottleid(), entity.getName(), entity.getVolume());
    }
}