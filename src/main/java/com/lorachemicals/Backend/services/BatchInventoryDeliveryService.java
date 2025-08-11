package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.BatchInventoryDeliveryResponseDTO;
import com.lorachemicals.Backend.model.*;
import com.lorachemicals.Backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BatchInventoryDeliveryService {

    @Autowired
    private BatchInventoryDeliveryRepository batchInventoryDeliveryRepository;

    public List<BatchInventoryDeliveryResponseDTO> getBatchInventoryByDelivery(Long deliveryid) {
        List<BatchInventoryDelivery> batchItems = batchInventoryDeliveryRepository.findById_Deliveryid(deliveryid);
        return batchItems.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<BatchInventoryDeliveryResponseDTO> getBatchInventoryByBatchType(Long batchtypeid) {
        List<BatchInventoryDelivery> batchItems = batchInventoryDeliveryRepository.findById_Batchtypeid(batchtypeid);
        return batchItems.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<BatchInventoryDeliveryResponseDTO> getBatchInventoryByType(String type) {
        List<BatchInventoryDelivery> batchItems = batchInventoryDeliveryRepository.findById_Type(type);
        return batchItems.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<BatchInventoryDeliveryResponseDTO> getBatchInventoryByWarehouseManager(Long wmid) {
        List<BatchInventoryDelivery> batchItems = batchInventoryDeliveryRepository.findByWarehouseManager_Wmid(wmid);
        return batchItems.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public BatchInventoryDeliveryResponseDTO updateQuantity(BatchInventoryDeliveryId id, Integer newQuantity, Integer newCurrentQuantity) {
        Optional<BatchInventoryDelivery> optionalBatchItem = batchInventoryDeliveryRepository.findById(id);
        if (optionalBatchItem.isPresent()) {
            BatchInventoryDelivery batchItem = optionalBatchItem.get();
            if (newQuantity != null) {
                batchItem.setQuantity(newQuantity);
            }
            if (newCurrentQuantity != null) {
                batchItem.setCurrentQuantity(newCurrentQuantity);
            }
            BatchInventoryDelivery savedItem = batchInventoryDeliveryRepository.save(batchItem);
            return convertToResponseDTO(savedItem);
        }
        throw new RuntimeException("BatchInventoryDelivery not found");
    }

    public void deleteBatchInventoryDelivery(BatchInventoryDeliveryId id) {
        batchInventoryDeliveryRepository.deleteById(id);
    }

    private BatchInventoryDeliveryResponseDTO convertToResponseDTO(BatchInventoryDelivery batchItem) {
        BatchInventoryDeliveryResponseDTO dto = new BatchInventoryDeliveryResponseDTO();
        dto.setBatchtypeid(batchItem.getId().getBatchtypeid());
        dto.setBatchTypeName(batchItem.getBatchType().getBatchtypename());
        dto.setDeliveryid(batchItem.getId().getDeliveryid());
        dto.setDatetime(batchItem.getId().getDatetime());
        dto.setType(batchItem.getId().getType());
        dto.setQuantity(batchItem.getQuantity());
        dto.setCurrentQuantity(batchItem.getCurrentQuantity());
        dto.setWmid(batchItem.getWarehouseManager().getWmid());
        dto.setWarehouseManagerName(batchItem.getWarehouseManager().getUser().getName());
        return dto;
    }
}
