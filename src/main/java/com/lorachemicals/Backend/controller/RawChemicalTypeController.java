package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.RawChemicalTypeResponseDTO;
import com.lorachemicals.Backend.model.RawChemicalType;
import com.lorachemicals.Backend.services.RawChemicalTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/raw-chemical-types")
public class RawChemicalTypeController {

    @Autowired
    private RawChemicalTypeService rawChemicalTypeService;

    // GET all
    @GetMapping("/all")
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            List<RawChemicalType> types = rawChemicalTypeService.getAllChemicalTypes();
            List<RawChemicalTypeResponseDTO> response = types.stream()
                    .map(type -> new RawChemicalTypeResponseDTO(
                            type.getChemid(),
                            type.getName(),
                            type.getDescription(),
                            type.getType()))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET by ID
    @GetMapping("/{chemid}")
    public ResponseEntity<?> getById(@PathVariable Long chemid, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            RawChemicalType type = rawChemicalTypeService.getChemicalTypeById(chemid)
                    .orElse(null);
            if (type == null) {
                return new ResponseEntity<>("Chemical type not found", HttpStatus.NOT_FOUND);
            }
            RawChemicalTypeResponseDTO response = new RawChemicalTypeResponseDTO(
                    type.getChemid(), type.getName(), type.getDescription(), type.getType());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // CREATE
    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody RawChemicalType newType, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            RawChemicalType saved = rawChemicalTypeService.createChemicalType(newType);
            return new ResponseEntity<>("Chemical type created with ID: " + saved.getChemid(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create chemical type", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // UPDATE
    @PutMapping("/{chemid}")
    public ResponseEntity<?> update(@PathVariable Long chemid,
                                    @RequestBody RawChemicalType updatedType,
                                    HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            RawChemicalType updated = rawChemicalTypeService.updateChemicalType(chemid, updatedType);
            return new ResponseEntity<>("Chemical type updated for ID: " + updated.getChemid(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update chemical type", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE
    @DeleteMapping("/{chemid}")
    public ResponseEntity<?> delete(@PathVariable Long chemid, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            rawChemicalTypeService.deleteChemicalType(chemid);
            return new ResponseEntity<>("Chemical type deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete chemical type", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
