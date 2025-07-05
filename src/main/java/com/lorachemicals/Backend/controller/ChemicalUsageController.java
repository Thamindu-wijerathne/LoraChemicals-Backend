package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.ChemicalUsageRequestDTO;
import com.lorachemicals.Backend.dto.ChemicalUsageResponseDTO;
import com.lorachemicals.Backend.model.ChemicalUsage;
import com.lorachemicals.Backend.services.ChemicalUsageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/chemical-usage")
public class ChemicalUsageController {

    @Autowired
    private ChemicalUsageService chemicalUsageService;

    @PostMapping("/add")
    public ResponseEntity<?> addChemicalUsage(@RequestBody ChemicalUsageRequestDTO chemicalUsageRequestDTO, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");

        try{
            ChemicalUsageResponseDTO created = chemicalUsageService.addChemicalUsage(chemicalUsageRequestDTO);
            return new ResponseEntity<>(created, HttpStatus.CREATED);

        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllChemicalUsage(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");

        try{
            List<ChemicalUsage> chemicalUsages = chemicalUsageService.getAllChemicalUsage();
            return new ResponseEntity<>(chemicalUsages, HttpStatus.OK);

        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/{prodid}/{inventoryid}")
    public ResponseEntity<?> getChemicalUsageById(@PathVariable Long prodid, @PathVariable Long inventoryid, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");

        try{
            ChemicalUsage cu = chemicalUsageService.getChemicalUsageById(prodid, inventoryid);
            return new ResponseEntity<>(cu, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{prodid}/{inventoryid}")
    public ResponseEntity<?> updateByid(@PathVariable Long prodid, @PathVariable Long inventoryid, @RequestBody ChemicalUsageRequestDTO chemicalUsageRequestDTO, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");

        try{
            ChemicalUsageResponseDTO updated = chemicalUsageService.updateChemicalUsage(prodid, inventoryid, chemicalUsageRequestDTO);
            return new ResponseEntity<>(updated, HttpStatus.OK);

        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{prodid}/{inventoryid}")
    public ResponseEntity<?> deleteByid(@PathVariable Long prodid, @PathVariable Long inventoryid, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");

        try{

            chemicalUsageService.deleteChemicalUsage(prodid, inventoryid);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
