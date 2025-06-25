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
        newBox.setcapacity(boxTypeRequestDTO.getcapacity());
        newBox.setName(boxTypeRequestDTO.getName());

        BoxType savedBoxType = boxTypeRepository.save(newBox);
        return convertToResponseDTO(savedBoxType);
    }

    public BoxTypeResponseDTO updateBoxType(Long boxId, BoxTypeRequestDTO boxTypeRequestDTO) {
        BoxType boxType = boxTypeRepository.findById(boxId)
                .orElseThrow(() -> new RuntimeException("Box type with id " + boxId + " does not exist"));

        boxType.setcapacity(boxTypeRequestDTO.getcapacity());
        boxType.setName(boxTypeRequestDTO.getName());
        BoxType updated = boxTypeRepository.save(boxType);
        return convertToResponseDTO(updated);
    }

    private BoxTypeResponseDTO convertToResponseDTO(BoxType boxType) {
        BoxTypeResponseDTO dto = new BoxTypeResponseDTO();
        dto.setBoxid(boxType.getBoxid());
        dto.setcapacity(boxType.getcapacity());
        dto.setName(boxType.getName());
        return dto;
    }
}
