package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.BoxRequestDTO;
import com.lorachemicals.Backend.dto.BoxResponseDTO;
import com.lorachemicals.Backend.services.BoxService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/box")
public class BoxController {

    @Autowired
    private BoxService boxService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        List<BoxResponseDTO> boxes = boxService.getAll();
        return ResponseEntity.ok(boxes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBox(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            boxService.deleteBox(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/byid/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            BoxResponseDTO box = boxService.getById(id);
            return ResponseEntity.ok(box);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody BoxRequestDTO boxRequestDTO, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            BoxResponseDTO response = boxService.createBox(boxRequestDTO);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody BoxRequestDTO boxRequestDTO, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            BoxResponseDTO response = boxService.updateBox(id, boxRequestDTO);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
