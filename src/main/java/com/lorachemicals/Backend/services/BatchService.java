package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.BatchRequestDTO;
import com.lorachemicals.Backend.model.*;
import com.lorachemicals.Backend.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BatchService {

    @Autowired
    BatchRepository batchRepository;

    @Autowired
    BatchTypeRepository batchTypeRepository;

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
    public List<Batch> getAllBatches() {
        try{
            return batchRepository.findAll();
        }
        catch(Exception e){
            throw new RuntimeException("Failed to find all batches: " + e.getMessage(), e);
        }
    }

    //get by id
    public Batch getBatchById(Long id) {
        try{
            return batchRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Failed to find batch by id: " + id));

        }
        catch(Exception e){
            throw new RuntimeException("Failed to find batch by id: " + id, e);
        }
    }

    //create
    @Transactional
    public Batch createBatch(BatchRequestDTO dto) {
        try {
            BatchType batchType = batchTypeRepository.findById(dto.getBatchtypeid())
                    .orElseThrow(() -> new RuntimeException("BatchType not found"));

            Box box = boxRepository.findById(dto.getInventoryid())
                    .orElseThrow(() -> new RuntimeException("Box not found"));

            WarehouseManager wm = warehouseManagerRepository.findById(dto.getWmid())
                    .orElseThrow(() -> new RuntimeException("Warehouse Manager not found"));

            BoxType boxType = box.getBoxType();

            int quantityToProduce = dto.getQuantity();

            ProductTypeVolume ptv = batchType.getProductTypeVolume();
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
            batch.setBatchtype(batchType);
            batch.setBatchdate(dto.getBatchdate() != null ? dto.getBatchdate() : LocalDateTime.now());
            batch.setBox(box);
            batch.setWarehousemanager(wm);
            batch.setQuantity(quantityToProduce);
            batch.setProduction(production);
            batch.setStatus("pending");

            return batchRepository.save(batch);

        } catch (Exception e) {
            throw new RuntimeException("Failed to create batch: " + e.getMessage(), e);
        }
    }


}
