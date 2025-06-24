package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.Solid_chemical_typeRequestDTO;
import com.lorachemicals.Backend.dto.Solid_chemical_typeResponseDTO;
import com.lorachemicals.Backend.services.Solid_chemical_typeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/solid-chemical-type")
public class Solid_chemical_typeController {

    @Autowired
    private Solid_chemical_typeService solidService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            List<Solid_chemical_typeResponseDTO> list = solidService.getAll();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            solidService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/byid/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            Solid_chemical_typeResponseDTO dto = solidService.getById(id);
            if (dto == null) {
                return new ResponseEntity<>("Solid chemical type not found", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody Solid_chemical_typeRequestDTO req, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            Solid_chemical_typeResponseDTO response = solidService.create(req);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Solid_chemical_typeRequestDTO req, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            Solid_chemical_typeResponseDTO response = solidService.update(id, req);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}