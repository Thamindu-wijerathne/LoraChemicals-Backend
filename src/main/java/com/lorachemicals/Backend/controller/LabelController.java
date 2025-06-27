package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.LabelRequestDTO;
import com.lorachemicals.Backend.model.Label;
import com.lorachemicals.Backend.services.LabelService;
import com.lorachemicals.Backend.util.AccessControlUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/labels")
public class LabelController {

    @Autowired
    private LabelService labelService;

    // GET all labels
    @GetMapping("/all")
    public ResponseEntity<?> getAllLabels(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            List<Label> labels = labelService.getAllLabels();
            return new ResponseEntity<>(labels, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get labels: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET label by inventory id
    @GetMapping("/{inventoryId}")
    public ResponseEntity<?> getLabelById(@PathVariable Long inventoryId, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            Optional<Label> label = labelService.getLabelById(inventoryId);
            if (label.isPresent()) {
                return new ResponseEntity<>(label.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Label not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get label: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // POST create new label
    @PostMapping("/create")
    public ResponseEntity<?> createLabel(@RequestBody LabelRequestDTO dto, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            Label created = labelService.createLabel(dto);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create label: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT update label by inventory id
    @PutMapping("/update/{inventoryId}")
    public ResponseEntity<?> updateLabel(@PathVariable Long inventoryId,
                                         @RequestBody LabelRequestDTO dto,
                                         HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            Label updated = labelService.updateLabel(inventoryId, dto);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update label: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE label by inventory id
    @DeleteMapping("/delete/{inventoryId}")
    public ResponseEntity<?> deleteLabel(@PathVariable Long inventoryId, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            labelService.deleteLabel(inventoryId);
            return new ResponseEntity<>("Label deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete label: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET sum of quantities grouped by label type
    @GetMapping("/quantities")
    public ResponseEntity<?> getQuantitySumGrouped(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            List<Object[]> quantitySums = labelService.getTotalQuantityGroupedByLabelType();
            return new ResponseEntity<>(quantitySums, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get quantity sums: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
