package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.model.RawMaterial;
import com.lorachemicals.Backend.services.RawMaterialService;
import com.lorachemicals.Backend.util.AccessControlUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/raw-materials")
public class RawMaterialController {

    private final RawMaterialService rawMaterialService;

    public RawMaterialController(RawMaterialService rawMaterialService) {
        this.rawMaterialService = rawMaterialService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllRawMaterials(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin", "warehouse");
        try {
            List<RawMaterial> rawMaterials = rawMaterialService.getAllRawMaterials();
            return ResponseEntity.ok(rawMaterials);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to fetch raw materials");
        }
    }

    @GetMapping("/all-low-stock")
    public ResponseEntity<?> getAllLowStockRawMaterials(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin", "warehouse");
        try {
            List<RawMaterial> rawMaterials = rawMaterialService.getAllLowStockRawMaterials();
            return ResponseEntity.ok(rawMaterials);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to fetch raw materials");
        }
    }
    @PostMapping("/add")
    public ResponseEntity<?> addRawMaterial(@RequestBody RawMaterial rawMaterial, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin", "warehouse");
        try {
            RawMaterial saved = rawMaterialService.saveRawMaterial(rawMaterial);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to add raw material");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRawMaterialById(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin", "warehouse");
        try {
            RawMaterial rm = rawMaterialService.getRawMaterialById(id).orElse(null);
            if (rm != null) {
                return ResponseEntity.ok(rm);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to fetch raw material by ID");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRawMaterial(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin", "warehouse");
        try {
            boolean deleted = rawMaterialService.deleteRawMaterial(id);
            if (deleted) {
                return ResponseEntity.ok("Deleted successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to delete raw material");
        }
    }
}
