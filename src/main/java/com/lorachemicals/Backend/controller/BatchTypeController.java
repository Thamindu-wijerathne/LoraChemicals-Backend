package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.BatchTypeRequestDTO;
import com.lorachemicals.Backend.dto.BatchTypeResponseDTO;
import com.lorachemicals.Backend.services.BatchTypeService;
import com.lorachemicals.Backend.util.AccessControlUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/batch-types")
public class BatchTypeController {

    @Autowired
    private BatchTypeService batchTypeService;

    @GetMapping("/all")
    public ResponseEntity<List<BatchTypeResponseDTO>> getAllBatchTypes(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin", "warehouse"); // ✅ Access check
        return ResponseEntity.ok(batchTypeService.getAllBatchTypes(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BatchTypeResponseDTO> getAllBatchTypesbyid(@PathVariable Long id,HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin"); // ✅ Access check
        return ResponseEntity.ok(batchTypeService.getAllBatchTypesbyid(id , request));
    }

    @PostMapping("/add")
    public ResponseEntity<BatchTypeResponseDTO> createBatchType(
            @RequestBody BatchTypeRequestDTO dto,
            HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin"); // ✅ Access check
        return ResponseEntity.ok(batchTypeService.createBatchType(dto, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BatchTypeResponseDTO> updateBatchType(
            @PathVariable Long id,
            @RequestBody BatchTypeRequestDTO dto,
            HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin"); // ✅ Access check
        return ResponseEntity.ok(batchTypeService.updateBatchType(id, dto, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBatchType(
            @PathVariable Long id,
            HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin"); // ✅ Access check
        batchTypeService.deleteBatchType(id, request);
        return ResponseEntity.ok("BatchType deleted successfully");
    }

}
