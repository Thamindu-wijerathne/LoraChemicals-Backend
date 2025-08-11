package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.BatchTypeRequestDTO;
import com.lorachemicals.Backend.dto.BatchTypeResponseDTO;
import com.lorachemicals.Backend.services.BatchTypeService;
import com.lorachemicals.Backend.util.AccessControlUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<?> getAllBatchTypesbyid(@PathVariable Long id, HttpServletRequest request) {
        try {
            AccessControlUtil.checkAccess(request, "admin", "warehouse"); // ✅ Access check
            BatchTypeResponseDTO batchTypeResponseDTO = batchTypeService.getAllBatchTypesbyid(id, request);
            return ResponseEntity.ok(batchTypeResponseDTO);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + ex.getMessage());
        }
    }


    @GetMapping("/ptv/{ptvid}")
    public ResponseEntity<List<BatchTypeResponseDTO>> getAllBatchTypesbyptvid(@PathVariable Long ptvid,HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin", "warehouse");
        try{
            return ResponseEntity.ok(batchTypeService.getAllByPtv(ptvid, request));
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
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
