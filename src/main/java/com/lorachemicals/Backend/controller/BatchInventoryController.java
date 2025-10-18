package com.lorachemicals.Backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lorachemicals.Backend.dto.BatchInventoryRequestDTO;
import com.lorachemicals.Backend.model.BatchInventory;
import com.lorachemicals.Backend.services.BatchInventoryService;
import com.lorachemicals.Backend.util.AccessControlUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/batch-inventory")
public class BatchInventoryController {

    @Autowired
    private BatchInventoryService batchInventoryService;

    // GET all batch inventories
    @GetMapping("/all")
    public ResponseEntity<?> getAllBatchInventories(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse", "admin","salesrep");
        try {
            List<BatchInventory> batchInventories = batchInventoryService.getAllBatchInventories();
            return new ResponseEntity<>(batchInventories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get batch inventories: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/low-stock")
    public ResponseEntity<?> getLowStockBatchInventories(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse", "admin","salesrep");
        try {
            List<BatchInventory> batchInventories = batchInventoryService.getLowStockBatchInventories();
            return new ResponseEntity<>(batchInventories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get batch inventories: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET batch inventory by inventory ID
    @GetMapping("/{inventoryId}")
    public ResponseEntity<?> getBatchInventoryById(@PathVariable Long inventoryId, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse", "admin");
        try {
            Optional<BatchInventory> batchInventory = batchInventoryService.getBatchInventoryById(inventoryId);
            if (batchInventory.isPresent()) {
                return new ResponseEntity<>(batchInventory.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Batch inventory not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get batch inventory: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET batch inventories by batch type ID
    @GetMapping("/batchtype/{batchTypeId}")
    public ResponseEntity<?> getBatchInventoriesByBatchTypeId(@PathVariable Long batchTypeId, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse", "admin");
        try {
            List<BatchInventory> batchInventories = batchInventoryService.getBatchInventoriesByBatchTypeId(batchTypeId);
            return new ResponseEntity<>(batchInventories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get batch inventories by batch type: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT update batch inventory by inventory ID
    @PutMapping("/{inventoryId}")
    public ResponseEntity<?> updateBatchInventory(@PathVariable Long inventoryId,
                                                  @RequestBody BatchInventoryRequestDTO dto,
                                                  HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse", "admin");
        try {
            BatchInventory updated = batchInventoryService.updateBatchInventory(inventoryId, dto);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update batch inventory: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // POST create new batch inventory
    @PostMapping("/add")
    public ResponseEntity<?> createBatchInventory(@RequestBody BatchInventoryRequestDTO dto,
                                                  HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse", "admin");
        try {
            BatchInventory created = batchInventoryService.createBatchInventory(dto);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create batch inventory: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT update batch quantity
    @PutMapping("/{inventoryId}/quantity")
    public ResponseEntity<?> updateBatchQuantity(@PathVariable Long inventoryId,
                                                 @RequestBody BatchInventoryRequestDTO dto,
                                                 HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse", "admin");
        try {
            BatchInventory updated = batchInventoryService.updateBatchQuantity(inventoryId, dto.getBatch_quantity());
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update batch quantity: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT update location
    @PutMapping("/{inventoryId}/location")
    public ResponseEntity<?> updateLocation(@PathVariable Long inventoryId,
                                            @RequestBody BatchInventoryRequestDTO dto,
                                            HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse", "admin");
        try {
            BatchInventory updated = batchInventoryService.updateLocation(inventoryId, dto.getLocation());
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update location: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE batch inventory by inventory ID
    @DeleteMapping("/{inventoryId}")
    public ResponseEntity<?> deleteBatchInventory(@PathVariable Long inventoryId, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse", "admin");
        try {
            batchInventoryService.deleteBatchInventory(inventoryId);
            return new ResponseEntity<>("Batch inventory deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete batch inventory: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}