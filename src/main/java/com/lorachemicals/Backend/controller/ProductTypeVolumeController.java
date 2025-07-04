package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.ProductTypeVolumeRequestDTO;
import com.lorachemicals.Backend.dto.ProductTypeVolumeResponseDTO;
import com.lorachemicals.Backend.services.ProductTypeVolumeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/producttypevolumes")
public class ProductTypeVolumeController {

    private final ProductTypeVolumeService service;
    private static final Logger logger = LoggerFactory.getLogger(ProductTypeVolumeController.class);

    @Value("${image.upload.dir:uploads/images}")
    private String uploadDir;

    public ProductTypeVolumeController(ProductTypeVolumeService service) {
        this.service = service;
    }

    // READ ALL
    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        try {
            List<ProductTypeVolumeResponseDTO> all = service.getAll();
            return ResponseEntity.ok(all);
        } catch (Exception e) {
            logger.error("Error fetching product list: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching products: " + e.getMessage());
        }
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            ProductTypeVolumeResponseDTO dto = service.getById(id);
            return dto != null ? ResponseEntity.ok(dto)
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found with id: " + id);
        } catch (Exception e) {
            logger.error("Error fetching product with id {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching product: " + e.getMessage());
        }
    }

    // CREATE
    @PostMapping("/add")
    public ResponseEntity<?> addproduct(
            @RequestPart("dto") ProductTypeVolumeRequestDTO dto,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
        try {
            ProductTypeVolumeResponseDTO newProduct = service.addProductvolumetype(dto, imageFile, uploadDir);
            return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error adding product: {}", e.getMessage(), e);
            return new ResponseEntity<>("Internal server error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestPart("dto") ProductTypeVolumeRequestDTO dto,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
        try {
            String imageFilename = null;
            if (imageFile != null && !imageFile.isEmpty()) {
                imageFilename = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename().replaceAll("[^a-zA-Z0-9.]", "_");
                File dir = new File(uploadDir);
                if (!dir.exists()) dir.mkdirs();
                File file = new File(dir, imageFilename);
                imageFile.transferTo(file);
            }
            ProductTypeVolumeResponseDTO updated = service.update(id, dto, imageFilename);
            if (updated == null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            logger.error("Error updating product: {}", e.getMessage(), e);
            return new ResponseEntity<>("Internal server error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            boolean deleted = service.delete(id);
            if (deleted)
                return ResponseEntity.ok().body("Deleted");
            else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        } catch (Exception e) {
            logger.error("Error deleting product: {}", e.getMessage(), e);
            return new ResponseEntity<>("Internal server error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // IMAGE SERVING
    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> serveImage(@PathVariable String filename) {
        try {
            File file = Paths.get(uploadDir, filename).toFile();
            if (!file.exists()) {
                logger.warn("Image file not found: {}", filename);
                return ResponseEntity.notFound().build();
            }

            Resource resource = new FileSystemResource(file);
            String contentType = determineContentType(filename);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .body(resource);
        } catch (Exception e) {
            logger.error("Error serving image {}: {}", filename, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private String determineContentType(String filename) {
        String extension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
        switch (extension) {
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";
            case "webp":
                return "image/webp";
            default:
                return "application/octet-stream";
        }
    }
}