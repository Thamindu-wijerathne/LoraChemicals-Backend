package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.*;
import com.lorachemicals.Backend.services.SupplierRawMaterialService;
import com.lorachemicals.Backend.util.AccessControlUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/srp")
public class SupplierRawMaterialController {

    @Autowired
    private SupplierRawMaterialService supplierRawMaterialService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            List<SupplierRawMaterialResponseDTO> list = supplierRawMaterialService.getAll();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch records: " + e.getMessage());
        }
    }

    // NEW: Combined purchase + inventory update
    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody PurchaseWithInventoryUpdateDTO dto, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            supplierRawMaterialService.createPurchaseAndUpdateInventory(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Purchase and inventory update successful.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        }
    }

    @GetMapping("/all/exp")
    public ResponseEntity<?> getAllExp(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try{
            List<SupplierRawMaterialResponseDTO> list = supplierRawMaterialService.getAllByexp();
            return ResponseEntity.ok(list);

        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch records: " + e.getMessage());
        }
    }

    @GetMapping("/{inventoryId}/{supplierId}/{date}")
    public ResponseEntity<?> getById(@PathVariable Long inventoryId,
                                     @PathVariable Long supplierId,
                                     @PathVariable String date,
                                     HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            LocalDateTime parsedDate = LocalDateTime.parse(date);
            SupplierRawMaterialResponseDTO dto = supplierRawMaterialService.getById(inventoryId, supplierId, parsedDate);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Record not found: " + e.getMessage());
        }
    }

    @GetMapping("/chem/{id}")
    public ResponseEntity<?> getChem(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            List<SupplierRawMaterialResponseDTO> list = supplierRawMaterialService.getByChemIdOrderByExp(id);
            return ResponseEntity.ok(list);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch records: " + e.getMessage());
        }
    }

    @PutMapping("/{inventoryId}/{supplierId}/{date}")
    public ResponseEntity<?> updateById(@PathVariable Long inventoryId,
                                        @PathVariable Long supplierId,
                                        @PathVariable String date,
                                        @RequestBody SupplierRawMaterialRequestDTO dto,
                                        HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            LocalDateTime parsedDate = LocalDateTime.parse(date);
            SupplierRawMaterialResponseDTO updated = supplierRawMaterialService.updateById(inventoryId, supplierId, parsedDate, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update: " + e.getMessage());
        }
    }

    @PutMapping("/{inventoryId}/{supplierId}/{date}/cquantity")
    public ResponseEntity<?> updateQuantity(@PathVariable Long inventoryId,
                                            @PathVariable Long supplierId,
                                            @PathVariable String date,
                                            @RequestBody SupplierRawMaterialRequestDTO dto,
                                            HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try{
            LocalDateTime parsedDate = LocalDateTime.parse(date);
            SupplierRawMaterialResponseDTO updated = supplierRawMaterialService.updateCQuantity(inventoryId, supplierId, parsedDate, dto.getCurrentQuantity());
            return ResponseEntity.ok(updated);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update: " + e.getMessage());
        }

    }

    @DeleteMapping("/{inventoryId}/{supplierId}/{date}")
    public ResponseEntity<?> deleteById(@PathVariable Long inventoryId,
                                        @PathVariable Long supplierId,
                                        @PathVariable String date,
                                        HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            LocalDateTime parsedDate = LocalDateTime.parse(date);
            supplierRawMaterialService.deleteById(inventoryId, supplierId, parsedDate);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete: " + e.getMessage());
        }
    }

//    @PostMapping("/add")
//    public ResponseEntity<?> create(@RequestBody SupplierRawMaterialRequestDTO dto, HttpServletRequest request) {
//        AccessControlUtil.checkAccess(request, "warehouse");
//        try {
//            SupplierRawMaterialResponseDTO created = supplierRawMaterialService.create(dto);
//            return ResponseEntity.ok(created);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Failed to create: " + e.getMessage());
//        }
//    }
}
