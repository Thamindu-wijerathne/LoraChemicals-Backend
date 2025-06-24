package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.RawMaterialTypeRequestDTO;
import com.lorachemicals.Backend.model.RawMaterialType;
import com.lorachemicals.Backend.services.RawMaterialTypeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/raw-material-type")
public class RawMaterialTypeController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private ModelMapper modelMapper;
    private final RawMaterialTypeService rawMaterialTypeService;

    @Autowired
    public RawMaterialTypeController(RawMaterialTypeService rawMaterialTypeService, ModelMapper modelMapper) {
        this.rawMaterialTypeService = rawMaterialTypeService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addRawMaterial(@RequestBody RawMaterialTypeRequestDTO RawMaterialDTO, HttpServletRequest request) {
        try {
            AccessControlUtil.checkAccess(request, "admin");

            RawMaterialType rawMaterialType = modelMapper.map(RawMaterialDTO, RawMaterialType.class);

            RawMaterialType savedRawMaterialType = rawMaterialTypeService.addRawMaterialType(rawMaterialType);
            logger.error("Raw Material: {}", savedRawMaterialType);
            return ResponseEntity.ok(rawMaterialType);
        } catch (Exception e) {
            logger.error("Error in add Raw material:", e);
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }
}
