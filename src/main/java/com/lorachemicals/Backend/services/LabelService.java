package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.LabelRequestDTO;
import com.lorachemicals.Backend.dto.LabelResponseDTO;
import com.lorachemicals.Backend.model.Label;
import com.lorachemicals.Backend.model.Labeltype;
import com.lorachemicals.Backend.model.RawMaterialType;
import com.lorachemicals.Backend.repository.LabelRepository;
import com.lorachemicals.Backend.repository.LabeltypeRepository;
import com.lorachemicals.Backend.repository.RawMaterialTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LabelService    {

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private LabeltypeRepository labeltypeRepository;

    @Autowired
    private RawMaterialTypeRepository rawMaterialTypeRepository;

    public List<LabelResponseDTO> getAll() {
        return labelRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public void deleteLabel(Long id) {
        if (!labelRepository.existsById(id)) {
            throw new RuntimeException("Label not found with id: " + id);
        }
        labelRepository.deleteById(id);
    }

    public LabelResponseDTO getById(Long id) {
        return labelRepository.findById(id)
                .map(this::convertToResponseDTO)
                .orElseThrow(() -> new RuntimeException("Label not found with id: " + id));
    }

    public LabelResponseDTO createLabel(LabelRequestDTO reqDTO) {
        Label label = new Label();

        // Set quantity
        label.setQuantity(reqDTO.getQuantity());

        // Set Labeltype FIRST - @MapsId will automatically set the labelid
        Labeltype labeltype = labeltypeRepository.findById(reqDTO.getLabelid())
                .orElseThrow(() -> new RuntimeException("Labeltype not found with id: " + reqDTO.getLabelid()));
        label.setLabeltype(labeltype);

        if (reqDTO.getRmtid() != null) {
            RawMaterialType rmt = rawMaterialTypeRepository.findById(reqDTO.getRmtid())
                    .orElseThrow(() -> new RuntimeException("RawMaterialType not found with id: " + reqDTO.getRmtid()));
            label.setRawMaterialType(rmt);
        } else {
            label.setRawMaterialType(null);
        }

        Label savedLabel = labelRepository.save(label);
        return convertToResponseDTO(savedLabel);
    }

    public LabelResponseDTO updateLabel(Long id, LabelRequestDTO reqDTO) {
        Label label = labelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Label not found with id: " + id));

        label.setQuantity(reqDTO.getQuantity());

        if (reqDTO.getRmtid() != null) {
            RawMaterialType rmt = rawMaterialTypeRepository.findById(reqDTO.getRmtid())
                    .orElseThrow(() -> new RuntimeException("RawMaterialType not found with id: " + reqDTO.getRmtid()));
            label.setRawMaterialType(rmt);
        } else {
            label.setRawMaterialType(null);
        }

        Label updatedLabel = labelRepository.save(label);
        return convertToResponseDTO(updatedLabel);
    }

    private LabelResponseDTO convertToResponseDTO(Label label) {
        LabelResponseDTO dto = new LabelResponseDTO();
        dto.setLabelid(label.getLabelid());
        dto.setQuantity(label.getQuantity());

        if (label.getLabeltype() != null) {
            dto.setLabeltypeName(label.getLabeltype().getName()); // Assuming Labeltype has getName()
            dto.setLabeltypeVolume(label.getLabeltype().getVolume()); // Assuming Labeltype has getVolume()
        }
        if (label.getRawMaterialType() != null) {
            dto.setRawMaterialTypeId(label.getRawMaterialType().getId());
            dto.setRawMaterialTypeName(label.getRawMaterialType().getName()); // Assuming RawMaterialType has getName()
        }
        return dto;
    }
}