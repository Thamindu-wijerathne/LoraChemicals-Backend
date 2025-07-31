package com.lorachemicals.Backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.lorachemicals.Backend.dto.BatchTypeWithoutBoxRequestDTO;
import com.lorachemicals.Backend.dto.BatchTypeWithoutBoxResponseDTO;
import com.lorachemicals.Backend.services.BatchTypeWithoutBoxService;
import com.lorachemicals.Backend.util.AccessControlUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/batch-types-without-box")
public class BatchTypeWithoutBoxController {

    @Autowired
    private BatchTypeWithoutBoxService batchTypeWithoutBoxService;

    @GetMapping("/all")
    public ResponseEntity<List<BatchTypeWithoutBoxResponseDTO>> getAllBatchTypesWithoutBox(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin", "warehouse"); // ✅ Access check
        return ResponseEntity.ok(batchTypeWithoutBoxService.getAllBatchTypesWithoutBox(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BatchTypeWithoutBoxResponseDTO> getAllBatchTypesWithoutBoxById(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin"); // ✅ Access check
        return ResponseEntity.ok(batchTypeWithoutBoxService.getAllBatchTypesWithoutBoxById(id, request));
    }

    @GetMapping("/ptv/{ptvid}")
    public ResponseEntity<List<BatchTypeWithoutBoxResponseDTO>> getAllBatchTypesWithoutBoxByPtvId(@PathVariable Long ptvid, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin", "warehouse");
        try {
            return ResponseEntity.ok(batchTypeWithoutBoxService.getAllByPtv(ptvid, request));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    

    @PostMapping("/add")
    public ResponseEntity<BatchTypeWithoutBoxResponseDTO> createBatchTypeWithoutBox(
            @RequestBody BatchTypeWithoutBoxRequestDTO dto,
            HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin"); // ✅ Access check
        return ResponseEntity.ok(batchTypeWithoutBoxService.createBatchTypeWithoutBox(dto, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BatchTypeWithoutBoxResponseDTO> updateBatchTypeWithoutBox(
            @PathVariable Long id,
            @RequestBody BatchTypeWithoutBoxRequestDTO dto,
            HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin"); // ✅ Access check
        return ResponseEntity.ok(batchTypeWithoutBoxService.updateBatchTypeWithoutBox(id, dto, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBatchTypeWithoutBox(
            @PathVariable Long id,
            HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin"); // ✅ Access check
        batchTypeWithoutBoxService.deleteBatchTypeWithoutBox(id, request);
        return ResponseEntity.ok("BatchTypeWithoutBox deleted successfully");
    }
}
