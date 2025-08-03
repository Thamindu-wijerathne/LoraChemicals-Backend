package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.model.BoxType;
import com.lorachemicals.Backend.repository.BoxTypeRepository;
import com.lorachemicals.Backend.dto.BoxTypeRequestDTO;
import com.lorachemicals.Backend.dto.BoxTypeResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoxTypeService {

    @Autowired
    private BoxTypeRepository boxTypeRepository;

    // Get all box types
    public List<BoxTypeResponseDTO> getAllBoxTypes() {
        return boxTypeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get box type by ID
    public BoxTypeResponseDTO getBoxTypeById(Long id) {
        try {
            Optional<BoxType> optional = boxTypeRepository.findById(id);
            return optional.map(this::convertToDTO).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Create a new box type
    public BoxTypeResponseDTO createBoxType(BoxTypeRequestDTO dto) {
        try {
            BoxType boxType = new BoxType();
            boxType.setName(dto.getName());
            boxType.setQuantityInBox(dto.getQuantityInBox());

            BoxType saved = boxTypeRepository.save(boxType);
            return convertToDTO(saved);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update an existing box type
    public BoxTypeResponseDTO updateBoxType(Long id, BoxTypeRequestDTO dto) {
        try {
            Optional<BoxType> optional = boxTypeRepository.findById(id);
            if (optional.isPresent()) {
                BoxType boxType = optional.get();
                boxType.setName(dto.getName());
                boxType.setQuantityInBox(dto.getQuantityInBox());

                BoxType updated = boxTypeRepository.save(boxType);
                return convertToDTO(updated);
            } else {
                return null; // not found
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Delete a box type
    public boolean deleteBoxType(Long id) {
        try {
            if (boxTypeRepository.existsById(id)) {
                boxTypeRepository.deleteById(id);
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
    private BoxTypeResponseDTO convertToDTO(BoxType entity) {
        BoxTypeResponseDTO dto = new BoxTypeResponseDTO();
        dto.setBoxid(entity.getBoxid());
        dto.setName(entity.getName());
        dto.setQuantityInBox(entity.getQuantityInBox());
        return dto;
    }
}
