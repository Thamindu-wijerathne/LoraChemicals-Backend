package com.lorachemicals.Backend.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lorachemicals.Backend.dto.BatchWithoutBoxRequestDTO;
import com.lorachemicals.Backend.model.BatchTypeWithoutBox;
import com.lorachemicals.Backend.model.BatchWithoutBox;
import com.lorachemicals.Backend.model.Bottle;
import com.lorachemicals.Backend.model.Bottletype;
import com.lorachemicals.Backend.model.Label;
import com.lorachemicals.Backend.model.Labeltype;
import com.lorachemicals.Backend.model.ProductType;
import com.lorachemicals.Backend.model.ProductTypeVolume;
import com.lorachemicals.Backend.model.Production;
import com.lorachemicals.Backend.model.WarehouseManager;
import com.lorachemicals.Backend.repository.BatchTypeWithoutBoxRepository;
import com.lorachemicals.Backend.repository.BatchWithoutBoxRepository;
import com.lorachemicals.Backend.repository.BottleRepository;
import com.lorachemicals.Backend.repository.LabelRepository;
import com.lorachemicals.Backend.repository.ProductionRepository;
import com.lorachemicals.Backend.repository.WarehouseManagerRepository;

import jakarta.transaction.Transactional;

@Service
public class BatchWithoutBoxService {

    @Autowired
    BatchWithoutBoxRepository batchWithoutBoxRepository;

    @Autowired
    BatchTypeWithoutBoxRepository batchTypeWithoutBoxRepository;

    @Autowired
    WarehouseManagerRepository warehouseManagerRepository;

    @Autowired
    ProductionRepository productionRepository;

    @Autowired
    BottleRepository bottleRepository;

    @Autowired
    LabelRepository labelRepository;

    //get all
    public List<BatchWithoutBox> getAllBatchesWithoutBox() {
        try {
            return batchWithoutBoxRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to find all batches without box: " + e.getMessage(), e);
        }
    }

    //get by id
    public BatchWithoutBox getBatchWithoutBoxById(Long id) {
        try {
            return batchWithoutBoxRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Failed to find batch without box by id: " + id));

        } catch (Exception e) {
            throw new RuntimeException("Failed to find batch without box by id: " + id, e);
        }
    }

    //create
    @Transactional
    public BatchWithoutBox createBatchWithoutBox(BatchWithoutBoxRequestDTO dto) {
        try {
            BatchTypeWithoutBox batchTypeWithoutBox = batchTypeWithoutBoxRepository.findById(dto.getBatchtypewithoutboxid())
                    .orElseThrow(() -> new RuntimeException("BatchTypeWithoutBox not found"));

            WarehouseManager wm = warehouseManagerRepository.findById(dto.getWmid())
                    .orElseThrow(() -> new RuntimeException("Warehouse Manager not found"));

            Production production = productionRepository.findById(dto.getProdid())
                    .orElseThrow(() -> new RuntimeException("Production not found"));

            int quantityToProduce = dto.getQuantity();

            ProductTypeVolume ptv = batchTypeWithoutBox.getProductTypeVolume();
            ProductType productType = ptv.getProducttype();

            // Calculate total volume required (without box calculations)
            long volumePerUnit = ptv.getVolume() != null ? ptv.getVolume() : 0L;
            Long requiredVolume = volumePerUnit * quantityToProduce;

            if (!"confirmed".equalsIgnoreCase(production.getStatus())) {
                throw new RuntimeException("Production is not confirmed");
            }

            if (production.getCurrentvolume() < requiredVolume) {
                throw new RuntimeException("Not enough production volume available");
            }

            // Check inventories (without box)
            Bottletype bottletype = ptv.getBottle();
            Labeltype labeltype = ptv.getLabel();

            if (bottletype == null) {
                throw new RuntimeException("Bottle type not found for product type volume");
            }

            if (labeltype == null) {
                throw new RuntimeException("Label type not found for product type volume");
            }

            Bottle bottle = bottleRepository.findByBottleType_Bottleid(bottletype.getBottleid())
                    .orElseThrow(() -> new RuntimeException("Bottle not found for bottle type"));

            Label label = labelRepository.findByLabeltype_LabelId(labeltype.getLabelid())
                    .orElseThrow(() -> new RuntimeException("Label not found for label type"));

            if (bottle.getQuantity() < quantityToProduce) {
                throw new RuntimeException("Not enough bottles available");
            }

            if (label.getQuantity() < quantityToProduce) {
                throw new RuntimeException("Not enough labels available");
            }

            // Update inventories
            production.setCurrentvolume(production.getCurrentvolume() - requiredVolume);
            bottle.setQuantity(bottle.getQuantity() - quantityToProduce);
            label.setQuantity(label.getQuantity() - quantityToProduce);

            // Save updated inventories
            productionRepository.save(production);
            bottleRepository.save(bottle);
            labelRepository.save(label);

            // Create batch without box
            BatchWithoutBox batchWithoutBox = new BatchWithoutBox();
            batchWithoutBox.setBatchtypewithoutbox(batchTypeWithoutBox);
            batchWithoutBox.setBatchdate(dto.getBatchdate() != null ? dto.getBatchdate() : LocalDateTime.now());
            batchWithoutBox.setWarehousemanager(wm);
            batchWithoutBox.setProduction(production);
            batchWithoutBox.setStatus(dto.getStatus() != null ? dto.getStatus() : "pending");
            batchWithoutBox.setQuantity(quantityToProduce);

            return batchWithoutBoxRepository.save(batchWithoutBox);

        } catch (Exception e) {
            throw new RuntimeException("Failed to create batch without box: " + e.getMessage(), e);
        }
    }

    //create by production id
    @Transactional
    public BatchWithoutBox createByProdid(Long prodid, BatchWithoutBoxRequestDTO dto) {
        try {
            dto.setProdid(prodid);
            return createBatchWithoutBox(dto);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create batch without box by production id: " + e.getMessage(), e);
        }
    }

    //update
    @Transactional
    public BatchWithoutBox updateBatchWithoutBox(Long batchid, BatchWithoutBoxRequestDTO dto) {
        try {
            BatchWithoutBox existingBatch = batchWithoutBoxRepository.findById(batchid)
                    .orElseThrow(() -> new RuntimeException("Batch without box not found"));

            if (dto.getBatchtypewithoutboxid() != null) {
                BatchTypeWithoutBox batchTypeWithoutBox = batchTypeWithoutBoxRepository.findById(dto.getBatchtypewithoutboxid())
                        .orElseThrow(() -> new RuntimeException("BatchTypeWithoutBox not found"));
                existingBatch.setBatchtypewithoutbox(batchTypeWithoutBox);
            }

            if (dto.getBatchdate() != null) {
                existingBatch.setBatchdate(dto.getBatchdate());
            }

            if (dto.getWmid() != null) {
                WarehouseManager wm = warehouseManagerRepository.findById(dto.getWmid())
                        .orElseThrow(() -> new RuntimeException("Warehouse Manager not found"));
                existingBatch.setWarehousemanager(wm);
            }

            if (dto.getProdid() != null) {
                Production production = productionRepository.findById(dto.getProdid())
                        .orElseThrow(() -> new RuntimeException("Production not found"));
                existingBatch.setProduction(production);
            }

            if (dto.getStatus() != null) {
                existingBatch.setStatus(dto.getStatus());
            }

            if (dto.getQuantity() > 0) {
                existingBatch.setQuantity(dto.getQuantity());
            }

            return batchWithoutBoxRepository.save(existingBatch);

        } catch (Exception e) {
            throw new RuntimeException("Failed to update batch without box: " + e.getMessage(), e);
        }
    }

    //delete
    @Transactional
    public void deleteBatchWithoutBox(Long batchid) {
        try {
            if (!batchWithoutBoxRepository.existsById(batchid)) {
                throw new RuntimeException("Batch without box not found");
            }
            batchWithoutBoxRepository.deleteById(batchid);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete batch without box: " + e.getMessage(), e);
        }
    }
}
