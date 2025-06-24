package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.BoxTypeRequestDTO;
import com.lorachemicals.Backend.dto.BoxTypeResponseDTO;
import com.lorachemicals.Backend.model.BoxType;
import com.lorachemicals.Backend.repository.BoxTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoxTypeService {

    @Autowired
    private BoxTypeRepository boxTypeRepository;

    public List<BoxTypeResponseDTO> getAllBoxTypes() {
        return boxTypeRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public void deleteBoxType(Long boxId) {
        if (!boxTypeRepository.existsById(boxId)) {
            throw new RuntimeException("Box type with id " + boxId + " does not exist");
        }
        boxTypeRepository.deleteById(boxId);
    }

    public BoxTypeResponseDTO getBoxType(Long boxId) {
        return boxTypeRepository.findById(boxId)
                .map(this::convertToResponseDTO)
                .orElseThrow(() -> new RuntimeException("Box type with id " + boxId + " not found"));
    }

    public BoxTypeResponseDTO createBoxType(BoxTypeRequestDTO boxTypeRequestDTO) {
        BoxType newBox = new BoxType();
        newBox.setQuantity_in_box(boxTypeRequestDTO.getQuantity_in_box());

        BoxType savedBoxType = boxTypeRepository.save(newBox);
        return convertToResponseDTO(savedBoxType);
    }

    public BoxTypeResponseDTO updateBoxType(Long boxId, BoxTypeRequestDTO boxTypeRequestDTO) {
        BoxType boxType = boxTypeRepository.findById(boxId)
                .orElseThrow(() -> new RuntimeException("Box type with id " + boxId + " does not exist"));

        boxType.setQuantity_in_box(boxTypeRequestDTO.getQuantity_in_box());
        BoxType updated = boxTypeRepository.save(boxType);
        return convertToResponseDTO(updated);
    }

    private BoxTypeResponseDTO convertToResponseDTO(BoxType boxType) {
        BoxTypeResponseDTO dto = new BoxTypeResponseDTO();
        dto.setBoxid(boxType.getBoxid());
        dto.setQuantity_in_box(boxType.getQuantity_in_box());
        return dto;
    }
}
