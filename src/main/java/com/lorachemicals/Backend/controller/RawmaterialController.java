package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.RawmaterialRequestDTO;
import com.lorachemicals.Backend.dto.RawmaterialResponseDTO;
import com.lorachemicals.Backend.services.RawmaterialService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rawmaterial")
public class RawmaterialController {

    @Autowired
    private RawmaterialService rawmaterialService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        List<RawmaterialResponseDTO> materials = rawmaterialService.getAll();
        return ResponseEntity.ok(materials);
    }

    @GetMapping("/byid/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            RawmaterialResponseDTO material = rawmaterialService.getById(id);
            return ResponseEntity.ok(material);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            rawmaterialService.deleteRawmaterial(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody RawmaterialRequestDTO reqDTO, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            RawmaterialResponseDTO response = rawmaterialService.createRawmaterial(reqDTO);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody RawmaterialRequestDTO reqDTO, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            RawmaterialResponseDTO response = rawmaterialService.updateRawmaterial(id, reqDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}