package com.lorachemicals.Backend.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lorachemicals.Backend.dto.BottletypeRequestDTO;
import com.lorachemicals.Backend.dto.BottletypeResponseDTO;
import com.lorachemicals.Backend.model.Bottle;
import com.lorachemicals.Backend.model.Bottletype;
import com.lorachemicals.Backend.repository.BottletypeRepository;

@Service
public class BottletypeService {

    @Autowired
    private BottletypeRepository bottletypeRepository;

    @Autowired
    private BottleService bottleService;

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
            if (!bottletypeRepository.existsById(id)) {
                return false; // not found
            }

            // Check if there are any bottles with this bottle type
            List<Bottle> bottles = bottleService.getBottlesByBottleTypeId(id);
            
            // Check if any bottle has quantity > 0
            boolean hasInventory = bottles.stream().anyMatch(bottle -> bottle.getQuantity() > 0);
            
            if (hasInventory) {
                throw new RuntimeException("Cannot delete bottle type because it has inventory in stock. Please clear the inventory first.");
            }

            // Delete all bottle inventory records with quantity = 0
            for (Bottle bottle : bottles) {
                bottleService.deleteBottle(bottle.getInventoryid());
            }

            // Now delete the bottle type
            bottletypeRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    // Convert entity to response DTO
    private BottletypeResponseDTO convertToDTO(Bottletype entity) {
        return new BottletypeResponseDTO(entity.getBottleid(), entity.getName(), entity.getVolume());
    }
}