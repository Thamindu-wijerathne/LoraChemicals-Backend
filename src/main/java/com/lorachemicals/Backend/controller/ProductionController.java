package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.ProductionDetailedResponseDTO;
import com.lorachemicals.Backend.dto.ProductionRequestDTO;
import com.lorachemicals.Backend.dto.ProductionResponseDTO;
import com.lorachemicals.Backend.model.Production;
import com.lorachemicals.Backend.services.ProductionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/production")
public class ProductionController {

    @Autowired
    private ProductionService productionService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try{
            List<Production> productions = productionService.findAll();
            return new ResponseEntity<>(productions, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try{
            Production production = productionService.findById(id);
            return new ResponseEntity<>(production, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/exp")
    public ResponseEntity<ProductionDetailedResponseDTO> getSoonestExpireProduction( HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            ProductionDetailedResponseDTO dto = productionService.getSoonestExpireProduction();
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

//    @PostMapping("/add")
//    public ResponseEntity<?> add(@RequestBody ProductionRequestDTO dto, HttpServletRequest request) {
//        AccessControlUtil.checkAccess(request, "warehouse");
//        try {
//            Production production = productionService.createProduction(dto);
//            return new ResponseEntity<>(production, HttpStatus.CREATED);
//        } catch (RuntimeException e) {
//            if (e.getMessage().contains("Insufficient") || e.getMessage().contains("Not enough")) {
//                // Business validation failure, return 400
//                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//            }
//            // Other errors -> 500
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
//        }
//    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProductionRequestDTO dto, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try{
            Production production = productionService.updateProduction(id, dto);
            return new ResponseEntity<>(production, HttpStatus.OK);

        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try{
            productionService.deleteProduction(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
