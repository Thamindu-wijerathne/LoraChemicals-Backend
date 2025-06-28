package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.RawChemicalRequestDTO;
import com.lorachemicals.Backend.model.Label;
import com.lorachemicals.Backend.model.RawChemical;
import com.lorachemicals.Backend.services.RawChemicalService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/raw-chemicals")
public class RawChemicalController {

    @Autowired
    private RawChemicalService rawChemicalService;

    // GET all raw chemicals
    @GetMapping("/all")
    public ResponseEntity<?> getAllRawChemicals(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            List<RawChemical> rawChemicals = rawChemicalService.getAllRawChemicals();
            return new ResponseEntity<>(rawChemicals, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get raw chemicals: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET raw chemical by inventory id
    @GetMapping("/{inventoryid}")
    public ResponseEntity<?> getRawChemicalById(@PathVariable Long inventoryid, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            Optional<RawChemical> rawChemical = rawChemicalService.getRawChemicalById(inventoryid);
            if (rawChemical.isPresent()) {
                return new ResponseEntity<>(rawChemical.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Raw chemical not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get raw chemical: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // POST create new raw chemical
    @PostMapping("/add")
    public ResponseEntity<?> createRawChemical(@RequestBody RawChemicalRequestDTO dto, HttpServletRequest request) {
        com.lorachemicals.Backend.util.AccessControlUtil.checkAccess(request, "warehouse");
        try {
            RawChemical created = rawChemicalService.createRawChemical(dto);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create raw chemical: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE raw chemical by inventory id
    @DeleteMapping("/{inventoryid}")
    public ResponseEntity<?> deleteRawChemical(@PathVariable Long inventoryid, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            rawChemicalService.deleteRawChemical(inventoryid);
            return new ResponseEntity<>("Raw chemical deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete raw chemical: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET sum of volumes grouped by chemical id
    @GetMapping("/volumes")
    public ResponseEntity<?> getVolumeSumGrouped(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            List<Object[]> volumeSums = rawChemicalService.getTotalVolumeGroupedByChemid();
            return new ResponseEntity<>(volumeSums, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get volume sums: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
