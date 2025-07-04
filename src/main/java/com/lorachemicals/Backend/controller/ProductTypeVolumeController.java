package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.ProductTypeVolumeRequestDTO;
import com.lorachemicals.Backend.dto.ProductTypeVolumeResponseDTO;
import com.lorachemicals.Backend.model.ProductTypeVolume;
import com.lorachemicals.Backend.services.ProductTypeVolumeService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-type-volumes")
public class ProductTypeVolumeController {

    private final ProductTypeVolumeService service;
    private static final Logger logger = LoggerFactory.getLogger(ProductTypeVolumeController.class);

    public ProductTypeVolumeController(ProductTypeVolumeService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        try {
            logger.info("GET /Get All ProductTypeVolumes API called");
            AccessControlUtil.checkAccess(request, "customer");
            List<ProductTypeVolumeResponseDTO> all = service.getAll();
            return ResponseEntity.ok(all);
        } catch (Exception e) {
            logger.error("Internal server error: {}", e.getMessage());
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
