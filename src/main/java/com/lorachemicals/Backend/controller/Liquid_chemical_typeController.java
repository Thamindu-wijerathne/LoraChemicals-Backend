package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.Liquid_chemical_typeRequestDTO;
import com.lorachemicals.Backend.dto.Liquid_chemical_typeResponseDTO;
import com.lorachemicals.Backend.services.Liquid_chemical_typeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/liquid-chemical-type")
public class Liquid_chemical_typeController {

    @Autowired
    private Liquid_chemical_typeService liquidService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            List<Liquid_chemical_typeResponseDTO> list = liquidService.getAll();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            liquidService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/byid/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            Liquid_chemical_typeResponseDTO dto = liquidService.getById(id);
            if (dto == null) {
                return new ResponseEntity<>("Liquid chemical type not found", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody Liquid_chemical_typeRequestDTO req, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            Liquid_chemical_typeResponseDTO response = liquidService.create(req);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Liquid_chemical_typeRequestDTO req, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            Liquid_chemical_typeResponseDTO response = liquidService.update(id, req);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}