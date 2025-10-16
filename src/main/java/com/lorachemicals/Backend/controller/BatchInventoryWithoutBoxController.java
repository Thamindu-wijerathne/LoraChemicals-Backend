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

import com.lorachemicals.Backend.dto.BatchInventoryWithoutBoxRequestDTO;
import com.lorachemicals.Backend.model.BatchInventoryWithoutBox;
import com.lorachemicals.Backend.services.BatchInventoryWithoutBoxService;
import com.lorachemicals.Backend.util.AccessControlUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/batch-inventory-without-box")
public class BatchInventoryWithoutBoxController {

    @Autowired
    private BatchInventoryWithoutBoxService batchInventoryWithoutBoxService;

    // GET all batch inventories without box
    @GetMapping("/all")
    public ResponseEntity<?> getAllBatchInventoriesWithoutBox(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse", "admin","salesrep");
        try {
            List<BatchInventoryWithoutBox> batchInventories = batchInventoryWithoutBoxService.getAllBatchInventoriesWithoutBox();
            return new ResponseEntity<>(batchInventories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get batch inventories without box: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET batch inventory without box by inventory ID
    @GetMapping("/{inventoryId}")
    public ResponseEntity<?> getBatchInventoryWithoutBoxById(@PathVariable Long inventoryId, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse", "admin");
        try {
            Optional<BatchInventoryWithoutBox> batchInventory = batchInventoryWithoutBoxService.getBatchInventoryWithoutBoxById(inventoryId);
            if (batchInventory.isPresent()) {
                return new ResponseEntity<>(batchInventory.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Batch inventory without box not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get batch inventory without box: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET batch inventories without box by batch type ID
    @GetMapping("/batchtype/{batchTypeId}")
    public ResponseEntity<?> getBatchInventoriesWithoutBoxByBatchTypeId(@PathVariable Long batchTypeId, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse", "admin");
        try {
            List<BatchInventoryWithoutBox> batchInventories = batchInventoryWithoutBoxService.getBatchInventoriesWithoutBoxByBatchTypeId(batchTypeId);
            return new ResponseEntity<>(batchInventories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get batch inventories without box by batch type: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET batch inventories without box by PTV ID
    @GetMapping("/ptv/{ptvid}")
    public ResponseEntity<?> getBatchInventoriesWithoutBoxByPtvId(@PathVariable Long ptvid, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse", "admin");
        try {
            List<BatchInventoryWithoutBox> batchInventories = batchInventoryWithoutBoxService.getBatchInventoriesWithoutBoxByPtvId(ptvid);
            return new ResponseEntity<>(batchInventories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get batch inventories without box by PTV ID: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT update batch inventory without box by inventory ID
    @PutMapping("/{inventoryId}")
    public ResponseEntity<?> updateBatchInventoryWithoutBox(@PathVariable Long inventoryId,
            @RequestBody BatchInventoryWithoutBoxRequestDTO dto,
            HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse", "admin");
        try {
            BatchInventoryWithoutBox updated = batchInventoryWithoutBoxService.updateBatchInventoryWithoutBox(inventoryId, dto);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update batch inventory without box: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // POST create new batch inventory without box
    @PostMapping("/add")
    public ResponseEntity<?> createBatchInventoryWithoutBox(@RequestBody BatchInventoryWithoutBoxRequestDTO dto,
            HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse", "admin");
        try {
            BatchInventoryWithoutBox created = batchInventoryWithoutBoxService.createBatchInventoryWithoutBox(dto);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create batch inventory without box: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT update batch quantity (like regular BatchInventoryController)
    @PutMapping("/{inventoryId}/quantity")
    public ResponseEntity<?> updateBatchQuantityPut(@PathVariable Long inventoryId,
            @RequestBody BatchInventoryWithoutBoxRequestDTO dto,
            HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse", "admin");
        try {
            BatchInventoryWithoutBox updated = batchInventoryWithoutBoxService.updateBatchQuantity(inventoryId, dto.getBatch_quantity());
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update batch quantity: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT update location
    @PutMapping("/{inventoryId}/location")
    public ResponseEntity<?> updateLocation(@PathVariable Long inventoryId,
            @RequestBody BatchInventoryWithoutBoxRequestDTO dto,
            HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse", "admin");
        try {
            BatchInventoryWithoutBox updated = batchInventoryWithoutBoxService.updateLocation(inventoryId, dto.getLocation());
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update location: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE batch inventory without box by inventory ID
    @DeleteMapping("/{inventoryId}")
    public ResponseEntity<?> deleteBatchInventoryWithoutBox(@PathVariable Long inventoryId,
            HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse", "admin");
        try {
            batchInventoryWithoutBoxService.deleteBatchInventoryWithoutBox(inventoryId);
            return new ResponseEntity<>("Batch inventory without box deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete batch inventory without box: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
