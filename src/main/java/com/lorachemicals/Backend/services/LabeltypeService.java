package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.model.Labeltype;
import com.lorachemicals.Backend.repository.LabeltypeRepository;
import com.lorachemicals.Backend.dto.LabeltypeRequestDTO;
import com.lorachemicals.Backend.dto.LabeltypeResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LabeltypeService {

    @Autowired
    private LabeltypeRepository labelRepo;

    // Get all label types
    public List<LabeltypeResponseDTO> getAll() {
        return labelRepo.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get label type by ID
    public LabeltypeResponseDTO getById(Long id) {
        return labelRepo.findById(id)
                .map(this::convertToResponseDTO)
                .orElse(null);
    }

    // Create a new label type
    public LabeltypeResponseDTO createLabeltype(LabeltypeRequestDTO reqDTO) {
        Labeltype newLabel = new Labeltype();
        newLabel.setName(reqDTO.getName());
        newLabel.setVolume(reqDTO.getVolume());
        Labeltype savedLabel = labelRepo.save(newLabel);
        return convertToResponseDTO(savedLabel);
    }

    // Update an existing label type
    public LabeltypeResponseDTO updateLabeltype(Long id, LabeltypeRequestDTO reqDTO) {
        Labeltype label = labelRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Label type not found with id: " + id));
        label.setName(reqDTO.getName());
        label.setVolume(reqDTO.getVolume());
        Labeltype updatedLabel = labelRepo.save(label);
        return convertToResponseDTO(updatedLabel);
    }

    // Delete a label type
    public void deleteLabeltype(Long id) {
        if (!labelRepo.existsById(id)) {
            throw new RuntimeException("Label type not found with id: " + id);
        }
        labelRepo.deleteById(id);
    }

    // Convert entity to response DTO
    private LabeltypeResponseDTO convertToResponseDTO(Labeltype label) {
        LabeltypeResponseDTO dto = new LabeltypeResponseDTO();
        dto.setlabelid(label.getlabelid());
        dto.setName(label.getName());
        dto.setVolume(label.getVolume());
        return dto;
    }
}