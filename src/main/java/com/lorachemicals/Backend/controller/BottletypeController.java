package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.BottletypeRequestDTO;
import com.lorachemicals.Backend.dto.BottletypeResponseDTO;
import com.lorachemicals.Backend.model.Bottletype;
import com.lorachemicals.Backend.services.BottletypeService;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bottletype")
public class BottletypeController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private ModelMapper modelMapper;

    @Autowired
    private BottletypeService bottletypeService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll(HttpServletRequest request){
        AccessControlUtil.checkAccess(request, "admin");
        try{
            List<BottletypeResponseDTO> bottletypes = bottletypeService.getAll();
            return new ResponseEntity<>(bottletypes, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBottletype(@PathVariable Long id , HttpServletRequest request){
        AccessControlUtil.checkAccess(request, "admin");
        try{
            bottletypeService.deleteBottletype(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/byid/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id, HttpServletRequest request){
        AccessControlUtil.checkAccess(request, "admin");
        try{
            BottletypeResponseDTO bottletype = bottletypeService.getById(id);
            return ResponseEntity.ok(bottletype);
        }catch(Exception e){
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody BottletypeRequestDTO req, HttpServletRequest request){
        AccessControlUtil.checkAccess(request, "admin");
        try{
            BottletypeResponseDTO response = bottletypeService.createBottletype(req);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody BottletypeRequestDTO req, HttpServletRequest request){
        AccessControlUtil.checkAccess(request, "admin");
        logger.error("Update Bottle: {}", req);

        try{
            BottletypeResponseDTO response = bottletypeService.updateBottletype(id, req);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}