package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.LabeltypeRequestDTO;
import com.lorachemicals.Backend.dto.LabeltypeResponseDTO;
import com.lorachemicals.Backend.services.LabeltypeService;
import com.lorachemicals.Backend.util.AccessControlUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/labeltype")
public class LabeltypeController {

    @Autowired
    private LabeltypeService labeltypeService;

    // Create label type
    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody LabeltypeRequestDTO req, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            LabeltypeResponseDTO response = labeltypeService.createLabelType(req);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all label types
    @GetMapping("/all")
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse", "admin");
        try {
            List<LabeltypeResponseDTO> list = labeltypeService.getAllLabeltypes();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get label type by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse_manager", "admin");
        try {
            LabeltypeResponseDTO dto = labeltypeService.getLabelTypeById(id);
            return (dto != null) ? ResponseEntity.ok(dto) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update label type
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody LabeltypeRequestDTO dto, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            LabeltypeResponseDTO updated = labeltypeService.updateLabelType(id, dto);
            return (updated != null) ? ResponseEntity.ok(updated) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete label type
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            boolean deleted = labeltypeService.deleteLabelType(id);
            return deleted ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
