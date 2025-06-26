package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.BoxRequestDTO;
import com.lorachemicals.Backend.dto.BoxResponseDTO;
import com.lorachemicals.Backend.model.Box;
import com.lorachemicals.Backend.model.BoxType;
import com.lorachemicals.Backend.model.RawMaterialType;
import com.lorachemicals.Backend.repository.BoxRepository;
import com.lorachemicals.Backend.repository.BoxTypeRepository;
import com.lorachemicals.Backend.repository.RawMaterialTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoxService {

    @Autowired
    private BoxRepository boxRepository;

    @Autowired
    private RawMaterialTypeRepository rawMaterialTypeRepository;

    @Autowired
    private BoxTypeRepository boxTypeRepository;

    public List<BoxResponseDTO> getAll() {
        return boxRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public void deleteBox(Long id) {
        if (!boxRepository.existsById(id)) {
            throw new RuntimeException("Box not found with id: " + id);
        }
        boxRepository.deleteById(id);
    }

    public BoxResponseDTO getById(Long id) {
        return boxRepository.findById(id)
                .map(this::convertToResponseDTO)
                .orElseThrow(() -> new RuntimeException("Box not found with id: " + id));
    }

    public BoxResponseDTO createBox(BoxRequestDTO reqDTO) {
        Box box = new Box();
        box.setBoxid(reqDTO.getBoxid());
        box.setQuantity(reqDTO.getQuantity());

        // Use boxid as boxTypeId since they're the same key in your model
        Long boxTypeId = reqDTO.getBoxid();
        BoxType boxType = boxTypeRepository.findById(boxTypeId)
                .orElseThrow(() -> new RuntimeException("BoxType not found with id: " + boxTypeId));
        box.setBoxType(boxType);
        System.out.println("boxType: " + boxType);
        if (reqDTO.getRmtid() != null) {
            RawMaterialType rmt = rawMaterialTypeRepository.findById(reqDTO.getRmtid())
                    .orElseThrow(() -> new RuntimeException("RawMaterialType not found with rmtid: " + reqDTO.getRmtid()));
            box.setRawMaterialType(rmt);
        } else {
            box.setRawMaterialType(null);
        }
        Box savedBox = boxRepository.save(box);
        System.out.println("savedBox: " + savedBox);
        reqDTO.toString();
        System.out.println("reqDTO: " + reqDTO);
        return convertToResponseDTO(savedBox);
    }

    public BoxResponseDTO updateBox(Long id, BoxRequestDTO reqDTO) {
        Box box = boxRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Box not found with id: " + id));

        box.setQuantity(reqDTO.getQuantity());

        // Update RawMaterialType (Optional)
        if (reqDTO.getRmtid() != null) {
            RawMaterialType rmt = rawMaterialTypeRepository.findById(reqDTO.getRmtid())
                    .orElseThrow(() -> new RuntimeException("RawMaterialType not found with rmtid: " + reqDTO.getRmtid()));
            box.setRawMaterialType(rmt);
        } else {
            box.setRawMaterialType(null);
        }

        Box updatedBox = boxRepository.save(box);
        return convertToResponseDTO(updatedBox);
    }

    private BoxResponseDTO convertToResponseDTO(Box box) {
        BoxResponseDTO dto = new BoxResponseDTO();
        dto.setBoxid(box.getBoxid());
        dto.setQuantity(box.getQuantity());

        if (box.getBoxType() != null) {
            dto.setBoxTypeId(box.getBoxType().getBoxid());
            dto.setBoxTypeName(box.getBoxType().getName()); // Assuming BoxType has getName()
        }

        if (box.getRawMaterialType() != null) {
            dto.setRmtid(box.getRawMaterialType().getId());
            dto.setRawMaterialTypeName(box.getRawMaterialType().getName()); // Assuming RawMaterialType has getName()
        }

        return dto;
    }
}