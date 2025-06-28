package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.model.Box;
import com.lorachemicals.Backend.model.BoxType;
import com.lorachemicals.Backend.repository.BoxRepository;
import com.lorachemicals.Backend.repository.BoxTypeRepository;
import com.lorachemicals.Backend.dto.BoxRequestDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoxService {

    @Autowired
    private BoxRepository boxRepository;

    @Autowired
    private BoxTypeRepository boxTypeRepository;

    // Get all boxes
    public List<Box> getAllBoxes() {
        try {
            return boxRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch boxes: " + e.getMessage(), e);
        }
    }

    // Get box by inventory ID
    public Optional<Box> getBoxById(Long inventoryId) {
        try {
            return boxRepository.findById(inventoryId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch box by ID: " + e.getMessage(), e);
        }
    }

    // Create new box
    public Box createBox(BoxRequestDTO dto) {
        try {
            BoxType boxType = boxTypeRepository.findById(dto.getBoxId())
                    .orElseThrow(() -> new RuntimeException("Box type not found"));

            Box box = new Box();
            box.setBoxType(boxType);
            box.setQuantity(dto.getQuantity());
            box.setLocation(dto.getLocation());

            return boxRepository.save(box);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create box: " + e.getMessage(), e);
        }
    }

    // Update existing box
    public Box updateBox(Long inventoryId, BoxRequestDTO dto) {
        try {
            Box box = boxRepository.findById(inventoryId)
                    .orElseThrow(() -> new RuntimeException("Box not found"));

            BoxType boxType = boxTypeRepository.findById(dto.getBoxId())
                    .orElseThrow(() -> new RuntimeException("Box type not found"));

            box.setBoxType(boxType);
            box.setQuantity(dto.getQuantity());
            box.setLocation(dto.getLocation());

            return boxRepository.save(box);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update box: " + e.getMessage(), e);
        }
    }

    // Delete box by inventory ID
    public void deleteBox(Long inventoryId) {
        try {
            boxRepository.deleteById(inventoryId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete box: " + e.getMessage(), e);
        }
    }


}
