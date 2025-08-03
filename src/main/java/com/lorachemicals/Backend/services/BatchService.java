package com.lorachemicals.Backend.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lorachemicals.Backend.dto.BatchRequestDTO;
import com.lorachemicals.Backend.dto.BatchResponseDTO;
import com.lorachemicals.Backend.model.Batch;
import com.lorachemicals.Backend.model.Bottle;
import com.lorachemicals.Backend.model.Bottletype;
import com.lorachemicals.Backend.model.Box;
import com.lorachemicals.Backend.model.BoxType;
import com.lorachemicals.Backend.model.Label;
import com.lorachemicals.Backend.model.Labeltype;
import com.lorachemicals.Backend.model.ParentBatchType;
import com.lorachemicals.Backend.model.ProductType;
import com.lorachemicals.Backend.model.ProductTypeVolume;
import com.lorachemicals.Backend.model.Production;
import com.lorachemicals.Backend.model.WarehouseManager;
import com.lorachemicals.Backend.repository.BatchRepository;
import com.lorachemicals.Backend.repository.BatchTypeRepository;
import com.lorachemicals.Backend.repository.BottleRepository;
import com.lorachemicals.Backend.repository.BoxRepository;
import com.lorachemicals.Backend.repository.LabelRepository;
import com.lorachemicals.Backend.repository.ParentBatchTypeRepository;
import com.lorachemicals.Backend.repository.ProductionRepository;
import com.lorachemicals.Backend.repository.WarehouseManagerRepository;

import jakarta.transaction.Transactional;

@Service
public class BatchService {

    @Autowired
    BatchRepository batchRepository;

    @Autowired
    BatchTypeRepository batchTypeRepository;

    @Autowired
    ParentBatchTypeRepository parentBatchTypeRepository;

    @Autowired
    BoxRepository boxRepository;

    @Autowired
    WarehouseManagerRepository warehouseManagerRepository;

    @Autowired
    ProductionRepository productionRepository;

    @Autowired
    BottleRepository bottleRepository;

    @Autowired
    LabelRepository labelRepository;

    //get all
    public List<BatchResponseDTO> getAllBatches() {
        try {
            return batchRepository.findAll()
                    .stream()
                    .map(this::convertToResponseDTO)
                    .collect(java.util.stream.Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to find all batches: " + e.getMessage(), e);
        }
    }

    //get by id
    public BatchResponseDTO getBatchById(Long id) {
        try {
            Batch batch = batchRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Failed to find batch by id: " + id));
            return convertToResponseDTO(batch);
        } catch (Exception e) {
            throw new RuntimeException("Failed to find batch by id: " + id, e);
        }
    }

    //create
    @Transactional
    public Batch createBatch(BatchRequestDTO dto) {
        try {
            // Get the parent batch type (either BatchType or BatchTypeWithoutBox)
            ParentBatchType parentBatchType = parentBatchTypeRepository.findById(dto.getEffectiveBatchTypeId())
                    .orElseThrow(() -> new RuntimeException("ParentBatchType not found"));

            Box box = boxRepository.findById(dto.getInventoryid())
                    .orElseThrow(() -> new RuntimeException("Box not found"));

            WarehouseManager wm = warehouseManagerRepository.findById(dto.getWmid())
                    .orElseThrow(() -> new RuntimeException("Warehouse Manager not found"));

            BoxType boxType = box.getBoxType();

            int quantityToProduce = dto.getQuantity();

            ProductTypeVolume ptv = parentBatchType.getProductTypeVolume();
            ProductType productType = ptv.getProducttype();

            // Calculate total volume required
            long volumePerUnit = ptv.getVolume() != null ? ptv.getVolume() : 0L;
            int quantityInBox = boxType.getQuantityInBox() != null ? boxType.getQuantityInBox() : 0;
            int packsNeeded = quantityToProduce * quantityInBox;
            Long requiredVolume = volumePerUnit * packsNeeded;

            // Get FIFO production for product type
            Production production = productionRepository
                    .findTopByProductype_ProductTypeIdOrderByExpiredateAsc(productType.getProductTypeId())
                    .orElseThrow(() -> new RuntimeException("No production found for product type"));

            if (!"confirmed".equalsIgnoreCase(production.getStatus())) {
                throw new RuntimeException("Production is not confirmed");
            }

            if (production.getCurrentvolume() < requiredVolume) {
                throw new RuntimeException("Not enough production volume available");
            }

            // Check inventories
            Bottletype bottletype = ptv.getBottle();
            Labeltype labeltype = ptv.getLabel();

            // Fetch actual inventory entities for bottle and label by type IDs
            Bottle bottle = bottleRepository.findByBottleType_Bottleid(bottletype.getBottleid())
                    .orElseThrow(() -> new RuntimeException("Bottle inventory not found for given type"));

            Label label = labelRepository.findByLabeltype_LabelId(labeltype.getLabelid())
                    .orElseThrow(() -> new RuntimeException("Label inventory not found"));

            int boxesRequired = dto.getQuantity();

            if (box.getQuantity() < boxesRequired) {
                throw new RuntimeException("Not enough boxes");
            }
            if (bottle.getQuantity() < packsNeeded) {
                throw new RuntimeException("Not enough bottles");
            }
            if (label.getQuantity() < packsNeeded) {
                throw new RuntimeException("Not enough labels");
            }

            // Deduct inventory quantities
            box.setQuantity(box.getQuantity() - boxesRequired);
            bottle.setQuantity(bottle.getQuantity() - packsNeeded);
            label.setQuantity(label.getQuantity() - packsNeeded);

            boxRepository.save(box);
            bottleRepository.save(bottle);
            labelRepository.save(label);

            // Deduct production volume
            production.setCurrentvolume(production.getCurrentvolume() - requiredVolume);
            productionRepository.save(production);

            // Create batch entity
            Batch batch = new Batch();
            batch.setParentBatchType(parentBatchType);
            LocalDateTime batchDate = dto.getBatchdate() != null ? dto.getBatchdate() : LocalDateTime.now();
            batch.setBatchdate(batchDate);
            batch.setBox(box);
            batch.setWarehousemanager(wm);
            batch.setQuantity(quantityToProduce);
            batch.setProduction(production);
            batch.setStatus("pending");
            batch.setBatchcode(generateBatchCode(batchDate));

            return batchRepository.save(batch);

        } catch (Exception e) {
            throw new RuntimeException("Failed to create batch: " + e.getMessage(), e);
        }
    }

    //create by prodid
    @Transactional
    public Batch createByProdid(Long prodid, BatchRequestDTO dto) {
        try {
            ParentBatchType parentBatchType = parentBatchTypeRepository.findById(dto.getEffectiveBatchTypeId())
                    .orElseThrow(() -> new RuntimeException("ParentBatchType not found"));

            Box box = boxRepository.findById(dto.getInventoryid())
                    .orElseThrow(() -> new RuntimeException("Box not found"));

            WarehouseManager wm = warehouseManagerRepository.findById(dto.getWmid())
                    .orElseThrow(() -> new RuntimeException("Warehouse Manager not found"));

            BoxType boxType = box.getBoxType();
            int quantityToProduce = dto.getQuantity();

            ProductTypeVolume ptv = parentBatchType.getProductTypeVolume();
            ProductType productType = ptv.getProducttype();

            long volumePerUnit = ptv.getVolume() != null ? ptv.getVolume() : 0L;
            int quantityInBox = boxType.getQuantityInBox() != null ? boxType.getQuantityInBox() : 0;
            int packsNeeded = quantityToProduce * quantityInBox;
            Long requiredVolume = volumePerUnit * packsNeeded;

            Production production = productionRepository.getByProdid(prodid)
                    .orElseThrow(() -> new RuntimeException("No production found for prodid: " + prodid));

            if (!"confirmed".equalsIgnoreCase(production.getStatus())) {
                throw new RuntimeException("Production is not confirmed");
            }

            if (production.getCurrentvolume() < requiredVolume) {
                throw new RuntimeException("Not enough production volume available");
            }

            Bottletype bottletype = ptv.getBottle();
            Labeltype labeltype = ptv.getLabel();

            Bottle bottle = bottleRepository.findByBottleType_Bottleid(bottletype.getBottleid())
                    .orElseThrow(() -> new RuntimeException("Bottle inventory not found for given type"));

            Label label = labelRepository.findByLabeltype_LabelId(labeltype.getLabelid())
                    .orElseThrow(() -> new RuntimeException("Label inventory not found"));

            int boxesRequired = dto.getQuantity();

            if (box.getQuantity() < boxesRequired) {
                throw new RuntimeException("Not enough boxes");
            }
            if (bottle.getQuantity() < packsNeeded) {
                throw new RuntimeException("Not enough bottles");
            }
            if (label.getQuantity() < packsNeeded) {
                throw new RuntimeException("Not enough labels");
            }

            box.setQuantity(box.getQuantity() - boxesRequired);
            bottle.setQuantity(bottle.getQuantity() - packsNeeded);
            label.setQuantity(label.getQuantity() - packsNeeded);

            boxRepository.save(box);
            bottleRepository.save(bottle);
            labelRepository.save(label);

            production.setCurrentvolume(production.getCurrentvolume() - requiredVolume);
            productionRepository.save(production);

            Batch batch = new Batch();
            batch.setParentBatchType(parentBatchType);
            LocalDateTime batchDate = dto.getBatchdate() != null ? dto.getBatchdate() : LocalDateTime.now();
            batch.setBatchdate(batchDate);
            batch.setBox(box);
            batch.setWarehousemanager(wm);
            batch.setQuantity(quantityToProduce);
            batch.setProduction(production);
            batch.setStatus("pending");
            batch.setBatchcode(generateBatchCode(batchDate));

            return batchRepository.save(batch);

        } catch (Exception e) {
            throw new RuntimeException("Failed to create batch: " + e.getMessage(), e);
        }
    }

    //update status
    public Batch updatebatch(Long batchid, BatchRequestDTO batchRequestDTO) {
        try {
            Batch batch = batchRepository.findById(batchid)
                    .orElseThrow(() -> new RuntimeException("Batch not found"));

            batch.setStatus(batchRequestDTO.getStatus());
            return batchRepository.save(batch);
        } catch (Exception e) {
            throw new RuntimeException("Error updating batch: " + e.getMessage());
        }
    }

    // Generate unique batch code for Batch (with box)
    private String generateBatchCode(LocalDateTime batchDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        String dateStr = batchDate.format(formatter);
        Random random = new Random();
        int randomNum = 1000 + random.nextInt(9000); // 4-digit random number
        return "BT-" + dateStr + randomNum;
    }

    // Conversion method to DTO
    private BatchResponseDTO convertToResponseDTO(Batch batch) {
        BatchResponseDTO dto = new BatchResponseDTO();

        dto.setBatchid(batch.getBatchid());
        dto.setBatchcode(batch.getBatchcode());
        dto.setParentBatchTypeId(batch.getParentBatchType().getId());
        dto.setUniqueBatchCode(batch.getParentBatchType().getUniqueBatchCode());
        dto.setBatchtypename(batch.getParentBatchType().getBatchtypename());
        dto.setBatchdate(batch.getBatchdate());

        // For backward compatibility
        if (batch.getBatchtype() != null) {
            dto.setBatchtypeid(batch.getBatchtype().getBatchtypeid());
        }

        if (batch.getBox() != null) {
            dto.setInventoryid(batch.getBox().getInventoryid());
            if (batch.getBox().getBoxType() != null) {
                dto.setBoxTypeName(batch.getBox().getBoxType().getName());
            }
        }

        if (batch.getWarehousemanager() != null) {
            dto.setWmid(batch.getWarehousemanager().getWmid());
            if (batch.getWarehousemanager().getUser() != null) {
                String fullName = batch.getWarehousemanager().getUser().getFname();
                if (batch.getWarehousemanager().getUser().getLname() != null) {
                    fullName += " " + batch.getWarehousemanager().getUser().getLname();
                }
                dto.setWarehouseManagerName(fullName);
            }
        }

        dto.setQuantity(batch.getQuantity());

        if (batch.getProduction() != null) {
            dto.setProdid(batch.getProduction().getProdid());
            dto.setProductionStatus(batch.getProduction().getStatus());
            dto.setExpiredate(batch.getProduction().getExpiredate());
        }

        dto.setStatus(batch.getStatus());

        return dto;
    }
}
