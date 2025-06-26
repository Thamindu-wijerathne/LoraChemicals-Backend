package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.RawChemicalRequestDTO;
import com.lorachemicals.Backend.dto.RawChemicalResponseDTO;
import com.lorachemicals.Backend.services.RawChemicalService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/raw-chemical")
public class RawChemicalController {

    @Autowired
    private RawChemicalService rawChemicalService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        List<RawChemicalResponseDTO> rawChemicals = rawChemicalService.getAll();
        return ResponseEntity.ok(rawChemicals);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRawChemical(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            rawChemicalService.deleteRawChemical(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/byid/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            RawChemicalResponseDTO rawChemical = rawChemicalService.getById(id);
            return ResponseEntity.ok(rawChemical);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody RawChemicalRequestDTO rawChemicalRequestDTO, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            RawChemicalResponseDTO response = rawChemicalService.createRawChemical(rawChemicalRequestDTO);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody RawChemicalRequestDTO rawChemicalRequestDTO, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            RawChemicalResponseDTO response = rawChemicalService.updateRawChemical(id, rawChemicalRequestDTO);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}