package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.BottletypeRequestDTO;
import com.lorachemicals.Backend.dto.BottletypeResponseDTO;
import com.lorachemicals.Backend.services.BottletypeService;
import com.lorachemicals.Backend.util.AccessControlUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bottletype")
public class BottletypeController {

    @Autowired
    private BottletypeService bottletypeService;

    // Create bottle type
    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody BottletypeRequestDTO req, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            BottletypeResponseDTO response = bottletypeService.createBottleType(req);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all bottle types
    @GetMapping("/all")
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse", "admin");
        try {
            List<BottletypeResponseDTO> list = bottletypeService.getAllBottletypes();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get bottle type by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse_manager", "admin");
        try {
            BottletypeResponseDTO dto = bottletypeService.getBottleTypeById(id);
            return (dto != null) ? ResponseEntity.ok(dto) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update bottle type
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody BottletypeRequestDTO dto, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            BottletypeResponseDTO updated = bottletypeService.updateBottleType(id, dto);
            return (updated != null) ? ResponseEntity.ok(updated) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete bottle type
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            boolean deleted = bottletypeService.deleteBottleType(id);
            return deleted ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}