package com.lorachemicals.Backend.controller;

import java.util.List;
import java.util.Optional;

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

import com.lorachemicals.Backend.dto.BoxQuantityUpdateDTO;
import com.lorachemicals.Backend.dto.BoxRequestDTO;
import com.lorachemicals.Backend.dto.BoxResponseDTO;
import com.lorachemicals.Backend.model.Box;
import com.lorachemicals.Backend.services.BoxService;
import com.lorachemicals.Backend.util.AccessControlUtil;

import jakarta.servlet.http.HttpServletRequest;

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

    @GetMapping("/bt/{boxid}")
    public ResponseEntity<?> getBoxByBoxId(@PathVariable Long boxid, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            BoxResponseDTO box = boxService.getByBoxid(boxid);
            return new ResponseEntity<>(box, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Failed to get box: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET all boxes by boxtype ID
    @GetMapping("/boxtype/{boxtypeId}")
    public ResponseEntity<?> getBoxesByBoxtypeId(@PathVariable Long boxtypeId, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse", "admin");
        try {
            List<Box> boxes = boxService.getBoxesByBoxTypeId(boxtypeId);
            return new ResponseEntity<>(boxes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get boxes by boxtype: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT update box by inventory ID
    @PutMapping("/{boxid}")
    public ResponseEntity<?> updateBox(@PathVariable Long boxid,
                                       @RequestBody BoxRequestDTO dto,
                                       HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            Box updated = boxService.updateBox(boxid, dto);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update box: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // POST create new box
    @PostMapping("/add")
    public ResponseEntity<?> createBox(@RequestBody BoxRequestDTO dto, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin" , "warehouse");
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

    @PutMapping("/{inventoryId}/location")
    public ResponseEntity<?> updateLocation(@PathVariable Long inventoryId,
                                            @RequestBody BoxQuantityUpdateDTO dto,
                                            HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            Box updated = boxService.updateLocation(inventoryId, dto.getLocation());
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
