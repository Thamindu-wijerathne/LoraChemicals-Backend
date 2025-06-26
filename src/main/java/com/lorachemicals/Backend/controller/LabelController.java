package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.LabelRequestDTO;
import com.lorachemicals.Backend.dto.LabelResponseDTO;
import com.lorachemicals.Backend.services.LabelService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        List<LabelResponseDTO> labels = labelService.getAll();
        return ResponseEntity.ok(labels);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLabel(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            labelService.deleteLabel(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/byid/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            LabelResponseDTO label = labelService.getById(id);
            return ResponseEntity.ok(label);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody LabelRequestDTO labelRequestDTO, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            LabelResponseDTO response = labelService.createLabel(labelRequestDTO);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody LabelRequestDTO labelRequestDTO, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            LabelResponseDTO response = labelService.updateLabel(id, labelRequestDTO);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}