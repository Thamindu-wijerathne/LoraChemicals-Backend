package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.model.Bottle;
import com.lorachemicals.Backend.model.Bottletype;
import com.lorachemicals.Backend.model.RawChemical;
import com.lorachemicals.Backend.repository.BottleRepository;
import com.lorachemicals.Backend.repository.BottletypeRepository;
import com.lorachemicals.Backend.dto.BottleRequestDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BottleService {

    @Autowired
    private BottleRepository bottleRepository;

    @Autowired
    private BottletypeRepository bottletypeRepository;

    // Get all bottles
    public List<Bottle> getAllBottles() {
        try {
            return bottleRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch bottles: " + e.getMessage(), e);
        }
    }

    // Get bottle by inventory ID
    public Optional<Bottle> getBottleById(Long inventoryId) {
        try {
            return bottleRepository.findById(inventoryId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch bottle by ID: " + e.getMessage(), e);
        }
    }

    // Create new bottle
    public Bottle createBottle(BottleRequestDTO dto) {
        try {
            Bottletype bottleType = bottletypeRepository.findById(dto.getBottleTypeId())
                    .orElseThrow(() -> new RuntimeException("Bottle type not found"));

            Bottle bottle = new Bottle();
            bottle.setBottleType(bottleType);
            bottle.setQuantity(dto.getQuantity());
            bottle.setLocation(dto.getLocation());

            return bottleRepository.save(bottle);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create bottle: " + e.getMessage(), e);
        }
    }

    // Update existing bottle
    public Bottle updateBottle(Long inventoryId, BottleRequestDTO dto) {
        try {
            Bottle bottle = bottleRepository.findById(inventoryId)
                    .orElseThrow(() -> new RuntimeException("Bottle not found"));

            Bottletype bottleType = bottletypeRepository.findById(dto.getBottleId())
                    .orElseThrow(() -> new RuntimeException("Bottle type not found"));

            bottle.setBottleType(bottleType);
            bottle.setQuantity(dto.getQuantity());
            bottle.setLocation(dto.getLocation()); // add if location is part of Bottle

            return bottleRepository.save(bottle);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update bottle: " + e.getMessage(), e);
        }
    }

    public Bottle updateQuantity(Long inventoryId, int quantity) {
        try {
            Bottle raw = bottleRepository.findById(inventoryId)
                    .orElseThrow(() -> new RuntimeException("Bottle not found"));

            raw.setQuantity(quantity);
            return bottleRepository.save(raw);
        } catch (Exception e) {
            throw new RuntimeException("Error updating volume: " + e.getMessage());
        }
    }

    public Bottle updatelocation(Long inventoryId, String location) {
        try {
            Bottle raw = bottleRepository.findById(inventoryId)
                    .orElseThrow(() -> new RuntimeException("Bottle not found"));

            raw.setLocation(location);
            return bottleRepository.save(raw);
        } catch (Exception e) {
            throw new RuntimeException("Error updating volume: " + e.getMessage());
        }
    }

    // Delete bottle by inventory ID
    public void deleteBottle(Long inventoryId) {
        try {
            bottleRepository.deleteById(inventoryId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete bottle: " + e.getMessage(), e);
        }
    }
}