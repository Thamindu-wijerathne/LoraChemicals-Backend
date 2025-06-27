package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.LabeltypeRequestDTO;
import com.lorachemicals.Backend.dto.LabeltypeResponseDTO;
import com.lorachemicals.Backend.model.Labeltype;
import com.lorachemicals.Backend.repository.LabeltypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LabeltypeService {

    @Autowired
    private LabeltypeRepository labeltypeRepository;

    // Get all label types
    public List<LabeltypeResponseDTO> getAllLabeltypes() {
        return labeltypeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get label type by ID
    public LabeltypeResponseDTO getLabelTypeById(Long id) {
        try {
            Optional<Labeltype> optional = labeltypeRepository.findById(id);
            return optional.map(this::convertToDTO).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Create a new label type
    public LabeltypeResponseDTO createLabelType(LabeltypeRequestDTO dto) {
        try {
            Labeltype labeltype = new Labeltype();
            labeltype.setName(dto.getName());
            labeltype.setVolume(dto.getVolume());

            Labeltype saved = labeltypeRepository.save(labeltype);
            return convertToDTO(saved);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update an existing label type
    public LabeltypeResponseDTO updateLabelType(Long id, LabeltypeRequestDTO dto) {
        try {
            Optional<Labeltype> optional = labeltypeRepository.findById(id);
            if (optional.isPresent()) {
                Labeltype labeltype = optional.get();
                labeltype.setName(dto.getName());
                labeltype.setVolume(dto.getVolume());

                Labeltype updated = labeltypeRepository.save(labeltype);
                return convertToDTO(updated);
            } else {
                return null; // not found
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Delete a label type
    public boolean deleteLabelType(Long id) {
        try {
            if (labeltypeRepository.existsById(id)) {
                labeltypeRepository.deleteById(id);
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
    private LabeltypeResponseDTO convertToDTO(Labeltype entity) {
        return new LabeltypeResponseDTO(entity.getLabelid(), entity.getName(), entity.getVolume());
    }
}
