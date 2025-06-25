package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.RawMaterialTypeRequestDTO;
import com.lorachemicals.Backend.dto.RawMaterialTypeResponseDTO;
import com.lorachemicals.Backend.model.RawMaterialType;
import com.lorachemicals.Backend.services.RawMaterialTypeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.springframework.web.servlet.function.ServerResponse.status;

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

    @GetMapping("/all")
    public ResponseEntity<?> allRawMaterials() {
        try {
            List<RawMaterialType> rawMaterials = rawMaterialTypeService.getAllRawMaterials();

//             Convert to DTO
            List<RawMaterialTypeResponseDTO> dtoList = rawMaterials.stream()
                    .map(material -> modelMapper.map(material, RawMaterialTypeResponseDTO.class))
                    .toList();
            logger.error("all Raw Materials 2: {}", dtoList);

            return ResponseEntity.ok(dtoList);
        } catch (Exception e) {
            logger.error("Error fetching raw materials:", e);
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRawMaterialType(@PathVariable Long id, @RequestBody RawMaterialTypeRequestDTO updateRawMaterialTypeDTO , HttpServletRequest request) {
        try {
            AccessControlUtil.checkAccess(request, "admin");

            RawMaterialType updateRawMaterialType = rawMaterialTypeService.updateRawMaterialType(id, updateRawMaterialTypeDTO);

            return ResponseEntity.ok(updateRawMaterialType);

        } catch (Exception e) {
            logger.error("Error Updating raw material:", e);
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRawMaterialType(@PathVariable Long id, HttpServletRequest request) {
        try {
            AccessControlUtil.checkAccess(request, "admin");

            boolean deleted = rawMaterialTypeService.deleteRawMaterialType(id);
            if (deleted) {
                return ResponseEntity.ok("RawMaterialType deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("RawMaterialType not found");
            }

        } catch (Exception e) {
            logger.error("Error Deleting raw material:", e);
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }
}
