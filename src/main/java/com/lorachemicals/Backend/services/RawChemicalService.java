package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.RawChemicalRequestDTO;
import com.lorachemicals.Backend.dto.RawChemicalResponseDTO;
import com.lorachemicals.Backend.model.RawChemical;
import com.lorachemicals.Backend.model.RawChemicalType;
import com.lorachemicals.Backend.model.RawMaterialType;
import com.lorachemicals.Backend.repository.RawChemicalRepository;
import com.lorachemicals.Backend.repository.RawChemicalTypeRepository;
import com.lorachemicals.Backend.repository.RawMaterialTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RawChemicalService {

    @Autowired
    private RawChemicalRepository rawChemicalRepository;

    @Autowired
    private RawChemicalTypeRepository rawChemicalTypeRepository;

    @Autowired
    private RawMaterialTypeRepository rawMaterialTypeRepository;

    public List<RawChemicalResponseDTO> getAll() {
        return rawChemicalRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public void deleteRawChemical(Long id) {
        if (!rawChemicalRepository.existsById(id)) {
            throw new RuntimeException("RawChemical not found with id: " + id);
        }
        rawChemicalRepository.deleteById(id);
    }

    public RawChemicalResponseDTO getById(Long id) {
        return rawChemicalRepository.findById(id)
                .map(this::convertToResponseDTO)
                .orElseThrow(() -> new RuntimeException("RawChemical not found with id: " + id));
    }

    public RawChemicalResponseDTO createRawChemical(RawChemicalRequestDTO reqDTO) {
        RawChemical rawChemical = new RawChemical();

        // DON'T set chemid manually when using @MapsId
        // rawChemical.setChemid(reqDTO.getChemid()); // Remove this line

        rawChemical.setType(reqDTO.getType());
        rawChemical.setVolume(reqDTO.getVolume());

        // Set RawChemicalType FIRST - @MapsId will automatically set the chemid
        RawChemicalType chemicalType = rawChemicalTypeRepository.findById(reqDTO.getChemid())
                .orElseThrow(() -> new RuntimeException("RawChemicalType not found with id: " + reqDTO.getChemid()));
        rawChemical.setChemicalType(chemicalType);

        System.out.println("chemicalType: " + chemicalType);

        if (reqDTO.getRmtid() != null) {
            RawMaterialType rmt = rawMaterialTypeRepository.findById(reqDTO.getRmtid())
                    .orElseThrow(() -> new RuntimeException("RawMaterialType not found with rmtid: " + reqDTO.getRmtid()));
            rawChemical.setRawMaterialType(rmt);
        } else {
            rawChemical.setRawMaterialType(null);
        }

        RawChemical savedRawChemical = rawChemicalRepository.save(rawChemical);
        System.out.println("savedRawChemical: " + savedRawChemical);
        System.out.println("reqDTO: " + reqDTO);

        return convertToResponseDTO(savedRawChemical);
    }

    public RawChemicalResponseDTO updateRawChemical(Long id, RawChemicalRequestDTO reqDTO) {
        RawChemical rawChemical = rawChemicalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RawChemical not found with id: " + id));

        rawChemical.setType(reqDTO.getType());
        rawChemical.setVolume(reqDTO.getVolume());

        // Update RawMaterialType (Optional)
        if (reqDTO.getRmtid() != null) {
            RawMaterialType rmt = rawMaterialTypeRepository.findById(reqDTO.getRmtid())
                    .orElseThrow(() -> new RuntimeException("RawMaterialType not found with rmtid: " + reqDTO.getRmtid()));
            rawChemical.setRawMaterialType(rmt);
        } else {
            rawChemical.setRawMaterialType(null);
        }

        RawChemical updatedRawChemical = rawChemicalRepository.save(rawChemical);
        return convertToResponseDTO(updatedRawChemical);
    }

    private RawChemicalResponseDTO convertToResponseDTO(RawChemical rawChemical) {
        RawChemicalResponseDTO dto = new RawChemicalResponseDTO();
        dto.setChemid(rawChemical.getChemid());
        dto.setType(rawChemical.getType());
        dto.setVolume(rawChemical.getVolume());

        if (rawChemical.getChemicalType() != null) {
            dto.setChemicalTypeId(rawChemical.getChemicalType().getChemid());
            dto.setChemicalTypeName(rawChemical.getChemicalType().getName()); // Assuming RawChemicalType has getName()
        }

        if (rawChemical.getRawMaterialType() != null) {
            dto.setrmtid(rawChemical.getRawMaterialType().getId());
            dto.setRawMaterialTypeName(rawChemical.getRawMaterialType().getName()); // Assuming RawMaterialType has getName()
        }

        return dto;
    }
}