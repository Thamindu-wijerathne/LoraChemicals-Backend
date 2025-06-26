package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.BoxTypeRequestDTO;
import com.lorachemicals.Backend.dto.BoxTypeResponseDTO;
import com.lorachemicals.Backend.services.BoxTypeService;
import com.lorachemicals.Backend.util.AccessControlUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boxtype")
public class BoxTypeController {

    @Autowired
    private BoxTypeService boxTypeService;

    // Create box type
    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody BoxTypeRequestDTO req, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            BoxTypeResponseDTO response = boxTypeService.createBoxType(req);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all box types
    @GetMapping("/all")
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse", "admin");
        try {
            List<BoxTypeResponseDTO> list = boxTypeService.getAllBoxTypes();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get box type by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse_manager", "admin");
        try {
            BoxTypeResponseDTO dto = boxTypeService.getBoxTypeById(id);
            return (dto != null) ? ResponseEntity.ok(dto) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update box type
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody BoxTypeRequestDTO dto, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            BoxTypeResponseDTO updated = boxTypeService.updateBoxType(id, dto);
            return (updated != null) ? ResponseEntity.ok(updated) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete box type
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            boolean deleted = boxTypeService.deleteBoxType(id);
            return deleted ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
