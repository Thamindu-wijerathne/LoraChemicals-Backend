package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.LabeltypeRequestDTO;
import com.lorachemicals.Backend.dto.LabeltypeResponseDTO;
import com.lorachemicals.Backend.services.LabeltypeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/labeltype")
public class LabeltypeController {

    @Autowired
    private LabeltypeService labeltypeService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll(HttpServletRequest request){
        AccessControlUtil.checkAccess(request, "admin");
        try{
            List<LabeltypeResponseDTO> labeltypes = labeltypeService.getAll();
            return new ResponseEntity<>(labeltypes, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLabeltype(@PathVariable Long id, HttpServletRequest request){
        AccessControlUtil.checkAccess(request, "admin");
        try{
            labeltypeService.deleteLabeltype(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/byid/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id, HttpServletRequest request){
        AccessControlUtil.checkAccess(request, "admin");
        try{
            LabeltypeResponseDTO labeltype = labeltypeService.getById(id);
            return ResponseEntity.ok(labeltype);
        }catch(Exception e){
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody LabeltypeRequestDTO req, HttpServletRequest request){
        AccessControlUtil.checkAccess(request, "admin");
        try{
            LabeltypeResponseDTO response = labeltypeService.createLabeltype(req);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody LabeltypeRequestDTO req, HttpServletRequest request){
        AccessControlUtil.checkAccess(request, "admin");
        try{
            LabeltypeResponseDTO response = labeltypeService.updateLabeltype(id, req);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}