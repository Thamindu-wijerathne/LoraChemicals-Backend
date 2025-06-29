package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.BottleQuantityUpdateDTO;
import com.lorachemicals.Backend.dto.BoxQuantityUpdateDTO;
import com.lorachemicals.Backend.dto.BoxRequestDTO;
import com.lorachemicals.Backend.model.Bottle;
import com.lorachemicals.Backend.model.Box;
import com.lorachemicals.Backend.services.BoxService;
import com.lorachemicals.Backend.util.AccessControlUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/box")
public class BoxController {

    @Autowired
    private BoxService boxService;

    // GET all boxes
    @GetMapping("/all")
    public ResponseEntity<?> getAllBoxes(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse", "admin");
        try {
            List<Box> boxes = boxService.getAllBoxes();
            return new ResponseEntity<>(boxes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get boxes: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET box by inventory ID
    @GetMapping("/{inventoryId}")
    public ResponseEntity<?> getBoxById(@PathVariable Long inventoryId, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            Optional<Box> box = boxService.getBoxById(inventoryId);
            if (box.isPresent()) {
                return new ResponseEntity<>(box.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Box not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get box: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT update box by inventory ID
    @PutMapping("/{inventoryId}")
    public ResponseEntity<?> updateBox(@PathVariable Long inventoryId,
                                       @RequestBody BoxRequestDTO dto,
                                       HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            Box updated = boxService.updateBox(inventoryId, dto);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update box: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // POST create new box
    @PostMapping("/add")
    public ResponseEntity<?> createBox(@RequestBody BoxRequestDTO dto, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse", "admin");
        try {
            Box created = boxService.createBox(dto);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create box: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{inventoryId}/quantity")
    public ResponseEntity<?> updateQuantity(@PathVariable Long inventoryId,
                                            @RequestBody BoxQuantityUpdateDTO dto,
                                            HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            Box updated = boxService.updateQuantity(inventoryId, dto.getQuantity());
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update quantity: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE box by inventory ID
    @DeleteMapping("/{inventoryId}")
    public ResponseEntity<?> deleteBox(@PathVariable Long inventoryId, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            boxService.deleteBox(inventoryId);
            return new ResponseEntity<>("Box deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete box: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
