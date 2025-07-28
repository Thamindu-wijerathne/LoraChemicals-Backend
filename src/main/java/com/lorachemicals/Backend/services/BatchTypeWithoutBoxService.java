package com.lorachemicals.Backend.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lorachemicals.Backend.dto.BatchTypeWithoutBoxRequestDTO;
import com.lorachemicals.Backend.dto.BatchTypeWithoutBoxResponseDTO;
import com.lorachemicals.Backend.model.BatchTypeWithoutBox;
import com.lorachemicals.Backend.model.ProductTypeVolume;
import com.lorachemicals.Backend.repository.BatchTypeWithoutBoxRepository;
import com.lorachemicals.Backend.repository.ProductTypeVolumeRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class BatchTypeWithoutBoxService {

    @Autowired
    private BatchTypeWithoutBoxRepository batchTypeWithoutBoxRepository;


    @Autowired
    private ProductTypeVolumeRepository productTypeVolumeRepository;

    public List<BatchTypeWithoutBoxResponseDTO> getAllBatchTypesWithoutBox(HttpServletRequest request) {
        return batchTypeWithoutBoxRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public BatchTypeWithoutBoxResponseDTO getAllBatchTypesWithoutBoxById(Long id, HttpServletRequest request) {
        BatchTypeWithoutBox batchTypeWithoutBox = batchTypeWithoutBoxRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BatchTypeWithoutBox not found"));
        return convertToResponseDTO(batchTypeWithoutBox);
    }

    // Get by ptv
    public List<BatchTypeWithoutBoxResponseDTO> getAllByPtv(Long id, HttpServletRequest request) {
        try {
            ProductTypeVolume ptv = productTypeVolumeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("ProductTypeVolume not found with id: " + id));

            return batchTypeWithoutBoxRepository.findByProductTypeVolume(ptv)
                    .stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to find BatchTypeWithoutBox by id: " + id);
        }
    }

    public BatchTypeWithoutBoxResponseDTO createBatchTypeWithoutBox(BatchTypeWithoutBoxRequestDTO dto, HttpServletRequest request) {
        ProductTypeVolume ptv = productTypeVolumeRepository.findById(dto.getPtvid())
                .orElseThrow(() -> new RuntimeException("ProductTypeVolume not found"));

        BatchTypeWithoutBox batchTypeWithoutBox = new BatchTypeWithoutBox();
        batchTypeWithoutBox.setProductTypeVolume(ptv);
        batchTypeWithoutBox.setBatchtypename(dto.getBatchtypename());

        return convertToResponseDTO(batchTypeWithoutBoxRepository.save(batchTypeWithoutBox));
    }

    public BatchTypeWithoutBoxResponseDTO updateBatchTypeWithoutBox(Long id, BatchTypeWithoutBoxRequestDTO dto, HttpServletRequest request) {
        BatchTypeWithoutBox batchTypeWithoutBox = batchTypeWithoutBoxRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BatchTypeWithoutBox not found"));

        ProductTypeVolume ptv = productTypeVolumeRepository.findById(dto.getPtvid())
                .orElseThrow(() -> new RuntimeException("ProductTypeVolume not found"));

        batchTypeWithoutBox.setProductTypeVolume(ptv);
        batchTypeWithoutBox.setBatchtypename(dto.getBatchtypename());

        return convertToResponseDTO(batchTypeWithoutBoxRepository.save(batchTypeWithoutBox));
    }

    public void deleteBatchTypeWithoutBox(Long id, HttpServletRequest request) {
        if (!batchTypeWithoutBoxRepository.existsById(id)) {
            throw new RuntimeException("BatchTypeWithoutBox not found");
        }
        batchTypeWithoutBoxRepository.deleteById(id);
    }

    private BatchTypeWithoutBoxResponseDTO convertToResponseDTO(BatchTypeWithoutBox bt) {
        BatchTypeWithoutBoxResponseDTO dto = new BatchTypeWithoutBoxResponseDTO();

        dto.setBatchtypewithoutboxid(bt.getBatchtypewithoutboxid());
        dto.setPtvid(bt.getProductTypeVolume().getPtvid());
        dto.setProducttypename(bt.getProductTypeVolume().getName());
        dto.setBatchtypename(bt.getBatchtypename());

        return dto;
    }
}
