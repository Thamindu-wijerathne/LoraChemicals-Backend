//package com.lorachemicals.Backend.controller;
//
//import com.lorachemicals.Backend.dto.ProductTypeRequestDto;
//import com.lorachemicals.Backend.dto.ProductTypeResponseDto;
//import com.lorachemicals.Backend.services.ProductTypeService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/product-types")
//public class ProductTypeController {
//
//    private final ProductTypeService service;
//
//    public ProductTypeController(ProductTypeService service) {
//        this.service = service;
//    }
//
//    @PostMapping
//    public ResponseEntity<ProductTypeResponseDto> create(@RequestBody ProductTypeRequestDto dto) {
//        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ProductTypeResponseDto> update(@PathVariable Long id, @RequestBody ProductTypeRequestDto dto) {
//        return ResponseEntity.ok(service.update(id, dto));
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ProductTypeResponseDto> getById(@PathVariable Long id) {
//        return ResponseEntity.ok(service.getById(id));
//    }
//
//    @GetMapping
//    public ResponseEntity<List<ProductTypeResponseDto>> getAll() {
//        return ResponseEntity.ok(service.getAll());
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        service.delete(id);
//        return ResponseEntity.noContent().build();
//    }
//}

package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.ProductTypeRequestDto;
import com.lorachemicals.Backend.dto.ProductTypeResponseDto;
import com.lorachemicals.Backend.services.ProductTypeService;
import com.lorachemicals.Backend.controller.AccessControlUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-types")
public class ProductTypeController {

    private final ProductTypeService service;
    private static final Logger logger = LoggerFactory.getLogger(ProductTypeController.class);

    public ProductTypeController(ProductTypeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProductTypeRequestDto dto, HttpServletRequest request) {
        try {
            logger.info("POST /Create ProductType API called");
            AccessControlUtil.checkAccess(request, "admin");
            ProductTypeResponseDto response = service.create(dto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            logger.error("Error creating product type: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Internal server error: {}", e.getMessage());
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProductTypeRequestDto dto, HttpServletRequest request) {
        try {
            logger.info("PUT /Update ProductType API called");
            AccessControlUtil.checkAccess(request, "admin");
            ProductTypeResponseDto response = service.update(id, dto);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            logger.warn("ProductType not found or invalid: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Internal server error: {}", e.getMessage());
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id, HttpServletRequest request) {
        try {
            logger.info("GET /Get ProductType by ID API called");
            AccessControlUtil.checkAccess(request, "admin");
            ProductTypeResponseDto response = service.getById(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            logger.warn("ProductType not found: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Internal server error: {}", e.getMessage());
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        try {
            logger.info("GET /Get All ProductTypes API called");
            AccessControlUtil.checkAccess(request, "admin");
            List<ProductTypeResponseDto> all = service.getAll();
            return ResponseEntity.ok(all);
        } catch (Exception e) {
            logger.error("Internal server error: {}", e.getMessage());
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, HttpServletRequest request) {
        try {
            logger.info("DELETE /Delete ProductType API called");
            AccessControlUtil.checkAccess(request, "admin");
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            logger.warn("ProductType not found or deletion failed: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Internal server error: {}", e.getMessage());
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
