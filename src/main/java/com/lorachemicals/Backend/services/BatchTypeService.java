package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.BatchTypeRequestDTO;
import com.lorachemicals.Backend.dto.BatchTypeResponseDTO;
import com.lorachemicals.Backend.model.BatchType;
import com.lorachemicals.Backend.model.BoxType;
import com.lorachemicals.Backend.model.ProductTypeVolume;
import com.lorachemicals.Backend.repository.BatchTypeRepository;
import com.lorachemicals.Backend.repository.BoxTypeRepository;
import com.lorachemicals.Backend.repository.ProductTypeVolumeRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BatchTypeService {

    @Autowired
    private BatchTypeRepository batchTypeRepository;

    @Autowired
    private ProductTypeVolumeRepository productTypeVolumeRepository;

    @Autowired
    private BoxTypeRepository boxTypeRepository;

    public List<BatchTypeResponseDTO> getAllBatchTypes(HttpServletRequest request) {
        return batchTypeRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public BatchTypeResponseDTO getAllBatchTypesbyid(Long id, HttpServletRequest request) {
        BatchType batchType = batchTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BatchType not found"));
        return convertToResponseDTO(batchType);
    }

    public BatchTypeResponseDTO createBatchType(BatchTypeRequestDTO dto, HttpServletRequest request) {
        ProductTypeVolume ptv = productTypeVolumeRepository.findById(dto.getPtvid())
                .orElseThrow(() -> new RuntimeException("ProductTypeVolume not found"));

        BoxType box = boxTypeRepository.findById(dto.getBoxid())
                .orElseThrow(() -> new RuntimeException("BoxType not found"));

        BatchType batchType = new BatchType();
      batchType.setProductTypeVolume(ptv);
        batchType.setBoxType(box);
        batchType.setBatchtypename(dto.getBatchtypename());

        return convertToResponseDTO(batchTypeRepository.save(batchType));
    }

    public BatchTypeResponseDTO updateBatchType(Long id, BatchTypeRequestDTO dto, HttpServletRequest request) {
        BatchType batchType = batchTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BatchType not found"));

        ProductTypeVolume ptv = productTypeVolumeRepository.findById(dto.getPtvid())
                .orElseThrow(() -> new RuntimeException("ProductTypeVolume not found"));

        BoxType box = boxTypeRepository.findById(dto.getBoxid())
                .orElseThrow(() -> new RuntimeException("BoxType not found"));

        batchType.setProductTypeVolume(ptv);
        batchType.setBoxType(box);
        batchType.setBatchtypename(dto.getBatchtypename());

        return convertToResponseDTO(batchTypeRepository.save(batchType));
    }

    public void deleteBatchType(Long id, HttpServletRequest request) {
        if (!batchTypeRepository.existsById(id)) {
            throw new RuntimeException("BatchType not found");
        }
        batchTypeRepository.deleteById(id);
    }

    private BatchTypeResponseDTO convertToResponseDTO(BatchType bt) {
        BatchTypeResponseDTO dto = new BatchTypeResponseDTO();

        dto.setBatchtypeid(bt.getBatchtypeid());

        dto.setPtvid(bt.getProductTypeVolume().getPtvid());
        dto.setProducttypename(bt.getProductTypeVolume().getName());

        dto.setBoxid(bt.getBoxType().getBoxid());
        dto.setName(bt.getBoxType().getName());
        dto.setQuantityInBox(bt.getBoxType().getQuantityInBox());

        dto.setBatchtypename(bt.getBatchtypename());

        return dto;
    }
}
