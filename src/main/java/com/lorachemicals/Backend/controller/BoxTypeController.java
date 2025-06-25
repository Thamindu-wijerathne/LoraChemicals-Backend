package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.BoxTypeRequestDTO;
import com.lorachemicals.Backend.dto.BoxTypeResponseDTO;
import com.lorachemicals.Backend.services.BoxTypeService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boxtype")
public class BoxTypeController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private BoxTypeService boxTypeService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllBoxTypes(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            List<BoxTypeResponseDTO> list = boxTypeService.getAllBoxTypes();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch box types");
        }
    }

    @GetMapping("/byid/{id}")
    public ResponseEntity<?> getBoxType(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            BoxTypeResponseDTO dto = boxTypeService.getBoxType(id);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> createBoxType(@RequestBody BoxTypeRequestDTO dto, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        logger.error("Update Bottle: {}", dto);
        try {
            BoxTypeResponseDTO created = boxTypeService.createBoxType(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create box type");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBoxType(@PathVariable Long id, @RequestBody BoxTypeRequestDTO dto, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            BoxTypeResponseDTO updated = boxTypeService.updateBoxType(id, dto);
            return ResponseEntity.status(HttpStatus.OK).body(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoxType(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            boxTypeService.deleteBoxType(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
