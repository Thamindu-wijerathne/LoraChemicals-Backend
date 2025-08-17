package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.BatchInventoryDeliveryResponseDTO;
import com.lorachemicals.Backend.model.BatchInventoryDeliveryId;
import com.lorachemicals.Backend.services.BatchInventoryDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/batch-inventory-delivery")
public class BatchInventoryDeliveryController {

    @Autowired
    private BatchInventoryDeliveryService batchInventoryDeliveryService;

    @GetMapping("/delivery/{deliveryid}")
    public ResponseEntity<List<BatchInventoryDeliveryResponseDTO>> getBatchInventoryByDelivery(@PathVariable Long deliveryid) {
        List<BatchInventoryDeliveryResponseDTO> batchItems = batchInventoryDeliveryService.getBatchInventoryByDelivery(deliveryid);
        return new ResponseEntity<>(batchItems, HttpStatus.OK);
    }

    @GetMapping("/batchtype/{batchtypeid}")
    public ResponseEntity<List<BatchInventoryDeliveryResponseDTO>> getBatchInventoryByBatchType(@PathVariable Long batchtypeid) {
        List<BatchInventoryDeliveryResponseDTO> batchItems = batchInventoryDeliveryService.getBatchInventoryByBatchType(batchtypeid);
        return new ResponseEntity<>(batchItems, HttpStatus.OK);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<BatchInventoryDeliveryResponseDTO>> getBatchInventoryByType(@PathVariable String type) {
        List<BatchInventoryDeliveryResponseDTO> batchItems = batchInventoryDeliveryService.getBatchInventoryByType(type);
        return new ResponseEntity<>(batchItems, HttpStatus.OK);
    }

    @GetMapping("/warehouse-manager/{wmid}")
    public ResponseEntity<List<BatchInventoryDeliveryResponseDTO>> getBatchInventoryByWarehouseManager(@PathVariable Long wmid) {
        List<BatchInventoryDeliveryResponseDTO> batchItems = batchInventoryDeliveryService.getBatchInventoryByWarehouseManager(wmid);
        return new ResponseEntity<>(batchItems, HttpStatus.OK);
    }

    @PutMapping("/update-quantity")
    public ResponseEntity<BatchInventoryDeliveryResponseDTO> updateQuantity(@RequestBody Map<String, Object> request) {
        try {
            // Extract composite key components
            Long batchtypeid = Long.valueOf(request.get("batchtypeid").toString());
            Long deliveryid = Long.valueOf(request.get("deliveryid").toString());
            LocalDateTime datetime = LocalDateTime.parse(request.get("datetime").toString());
            String type = request.get("type").toString();
            
            BatchInventoryDeliveryId id = new BatchInventoryDeliveryId(batchtypeid, deliveryid, datetime, type);
            
            Integer newQuantity = request.get("quantity") != null ? 
                Integer.valueOf(request.get("quantity").toString()) : null;
            Integer newCurrentQuantity = request.get("currentQuantity") != null ? 
                Integer.valueOf(request.get("currentQuantity").toString()) : null;

            BatchInventoryDeliveryResponseDTO responseDTO = batchInventoryDeliveryService.updateQuantity(id, newQuantity, newCurrentQuantity);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteBatchInventoryDelivery(@RequestBody Map<String, Object> request) {
        try {
            // Extract composite key components
            Long batchtypeid = Long.valueOf(request.get("batchtypeid").toString());
            Long deliveryid = Long.valueOf(request.get("deliveryid").toString());
            LocalDateTime datetime = LocalDateTime.parse(request.get("datetime").toString());
            String type = request.get("type").toString();
            
            BatchInventoryDeliveryId id = new BatchInventoryDeliveryId(batchtypeid, deliveryid, datetime, type);
            batchInventoryDeliveryService.deleteBatchInventoryDelivery(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
