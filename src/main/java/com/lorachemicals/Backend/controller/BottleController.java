package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.BottleQuantityUpdateDTO;
import com.lorachemicals.Backend.dto.BottleRequestDTO;
import com.lorachemicals.Backend.dto.ChemicalVolumeUpdateDTO;
import com.lorachemicals.Backend.model.Bottle;
import com.lorachemicals.Backend.model.RawChemical;
import com.lorachemicals.Backend.services.BottleService;
import com.lorachemicals.Backend.util.AccessControlUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bottle")
public class BottleController {

    @Autowired
    private BottleService bottleService;

    // GET all bottles
    @GetMapping("/all")
    public ResponseEntity<?> getAllBottles(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse", "admin");
        try {
            List<Bottle> bottles = bottleService.getAllBottles();
            return new ResponseEntity<>(bottles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get bottles: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET bottle by inventory ID
    @GetMapping("/{inventoryId}")
    public ResponseEntity<?> getBottleById(@PathVariable Long inventoryId, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            Optional<Bottle> bottle = bottleService.getBottleById(inventoryId);
            if (bottle.isPresent()) {
                return new ResponseEntity<>(bottle.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Bottle not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get bottle: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{inventoryId}/quantity")
    public ResponseEntity<?> updateQuantity(@PathVariable Long inventoryId,
                                            @RequestBody BottleQuantityUpdateDTO dto,
                                            HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            Bottle updated = bottleService.updateQuantity(inventoryId, dto.getQuantity());
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update volume: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{inventoryId}/location")
    public ResponseEntity<?> updatelocation(@PathVariable Long inventoryId,
                                            @RequestBody BottleQuantityUpdateDTO dto,
                                            HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            Bottle updated = bottleService.updatelocation(inventoryId, dto.getLocation());
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update volume: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // POST create new bottle
    @PostMapping("/add")
    public ResponseEntity<?> createBottle(@RequestBody BottleRequestDTO dto, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse", "admin");
        try {
            Bottle created = bottleService.createBottle(dto);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create bottle: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT update bottle by inventory ID
    @PutMapping("/{inventoryId}")
    public ResponseEntity<?> updateBottle(@PathVariable Long inventoryId,
                                          @RequestBody BottleRequestDTO dto,
                                          HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            Bottle updated = bottleService.updateBottle(inventoryId, dto);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update bottle: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE bottle by inventory ID
    @DeleteMapping("/{inventoryId}")
    public ResponseEntity<?> deleteBottle(@PathVariable Long inventoryId, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            bottleService.deleteBottle(inventoryId);
            return new ResponseEntity<>("Bottle deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete bottle: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
