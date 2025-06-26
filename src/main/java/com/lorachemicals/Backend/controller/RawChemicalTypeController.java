package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.RawChemicalTypeRequestDTO;
import com.lorachemicals.Backend.dto.RawChemicalTypeResponseDTO;
import com.lorachemicals.Backend.services.RawChemicalTypeService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rawchemicaltype")
public class RawChemicalTypeController {
    private static final Logger logger = LoggerFactory.getLogger(RawChemicalTypeController.class);

    @Autowired
    private RawChemicalTypeService rawChemicalTypeService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllRawChemicalTypes(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            List<RawChemicalTypeResponseDTO> list = rawChemicalTypeService.getAllRawChemicalTypes();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch raw chemical types");
        }
    }

    @GetMapping("/byid/{id}")
    public ResponseEntity<?> getRawChemicalType(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            RawChemicalTypeResponseDTO dto = rawChemicalTypeService.getRawChemicalType(id);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> createRawChemicalType(@RequestBody RawChemicalTypeRequestDTO dto, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        logger.info("Create RawChemicalType: {}", dto);
        try {
            RawChemicalTypeResponseDTO created = rawChemicalTypeService.createRawChemicalType(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create raw chemical type");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRawChemicalType(@PathVariable Long id, @RequestBody RawChemicalTypeRequestDTO dto, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            RawChemicalTypeResponseDTO updated = rawChemicalTypeService.updateRawChemicalType(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRawChemicalType(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            rawChemicalTypeService.deleteRawChemicalType(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}