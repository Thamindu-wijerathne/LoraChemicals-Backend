package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.ChemicalVolumeUpdateDTO;
import com.lorachemicals.Backend.dto.RawChemicalRequestDTO;
import com.lorachemicals.Backend.model.RawChemical;
import com.lorachemicals.Backend.services.RawChemicalService;
import com.lorachemicals.Backend.util.AccessControlUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/chemical")
public class RawChemicalController { // ✅ You missed this line

    @Autowired
    private RawChemicalService rawChemicalService;

    // GET all raw chemicals
    @GetMapping("/all")
    public ResponseEntity<?> getAllRawChemicals(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse", "admin");
        try {
            List<RawChemical> rawChemicals = rawChemicalService.getAllRawChemicals();
            return new ResponseEntity<>(rawChemicals, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get raw chemicals: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET raw chemical by inventory ID
    @GetMapping("/{inventoryId}")
    public ResponseEntity<?> getRawChemicalById(@PathVariable Long inventoryId, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            Optional<RawChemical> rawChemical = rawChemicalService.getRawChemicalById(inventoryId);
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
        AccessControlUtil.checkAccess(request, "warehouse", "admin");
        try {
            RawChemical created = rawChemicalService.createRawChemical(dto);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create raw chemical: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT update raw chemical by inventory ID
    @PutMapping("/{inventoryId}")
    public ResponseEntity<?> updateRawChemical(@PathVariable Long inventoryId,
                                               @RequestBody RawChemicalRequestDTO dto,
                                               HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            RawChemical updated = rawChemicalService.updateRawChemical(inventoryId, dto);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update raw chemical: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT update volume
    @PutMapping("/{inventoryId}/quantity")
    public ResponseEntity<?> updateVolume(@PathVariable Long inventoryId,
                                          @RequestBody ChemicalVolumeUpdateDTO dto,
                                          HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            RawChemical updated = rawChemicalService.updateVolume(inventoryId, dto.getVolume());
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update volume: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE raw chemical by inventory ID
    @DeleteMapping("/{inventoryId}")
    public ResponseEntity<?> deleteRawChemical(@PathVariable Long inventoryId, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            rawChemicalService.deleteRawChemical(inventoryId);
            return new ResponseEntity<>("Raw chemical deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete raw chemical: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
