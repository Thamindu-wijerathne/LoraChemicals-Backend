package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.ProductTypeVolumeRequestDTO;
import com.lorachemicals.Backend.dto.ProductTypeVolumeResponseDTO;
import com.lorachemicals.Backend.services.ProductTypeVolumeService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

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

    // CREATE
    @PostMapping("/add")
    public ResponseEntity<?> addproduct(
            HttpServletRequest request ,
            @RequestPart("dto") ProductTypeVolumeRequestDTO dto,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
        try {
            AccessControlUtil.checkAccess(request,  "admin");
            // Validate image file if provided
            if (imageFile != null && !imageFile.isEmpty()) {
                validateImageFile(imageFile);
            }

            ProductTypeVolumeResponseDTO newProduct = service.addProductvolumetype(dto, imageFile, uploadDir);
            return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input: {}", e.getMessage());
            return new ResponseEntity<>("Invalid input: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            logger.error("Error saving image: {}", e.getMessage(), e);
            return new ResponseEntity<>("Error saving image: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("Error adding product: {}", e.getMessage(), e);
            return new ResponseEntity<>("Internal server error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

    // UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
            HttpServletRequest request,
            @PathVariable Long id,
            @RequestPart("dto") ProductTypeVolumeRequestDTO dto,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
        try {
            AccessControlUtil.checkAccess(request,  "admin");

            // Validate image file if provided
            if (imageFile != null && !imageFile.isEmpty()) {
                validateImageFile(imageFile);
            }

            ProductTypeVolumeResponseDTO updated = service.update(id, dto, imageFile, uploadDir);
            if (updated == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found with id: " + id);
            }
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input: {}", e.getMessage());
            return new ResponseEntity<>("Invalid input: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            logger.error("Error saving image: {}", e.getMessage(), e);
            return new ResponseEntity<>("Error saving image: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("Error updating product: {}", e.getMessage(), e);
            return new ResponseEntity<>("Internal server error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id,HttpServletRequest request) {
        try {
            AccessControlUtil.checkAccess(request, "admin");
            boolean deleted = service.delete(id);
            if (deleted) {
                return ResponseEntity.ok().body("Product deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found with id: " + id);
            }
        } catch (Exception e) {
            logger.error("Error deleting product: {}", e.getMessage(), e);
            return new ResponseEntity<>("Internal server error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // IMAGE SERVING
    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> serveImage(
            @PathVariable String filename,
            @RequestParam(required = false) String token,
            HttpServletRequest request) {
        try {
            // Check for token in query parameter first, then in Authorization header
            String authToken = token;
            if (authToken == null || authToken.isEmpty()) {
                String authHeader = request.getHeader("Authorization");
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authToken = authHeader.substring(7);
                }
            }

            // Validate token (you'll need to implement this based on your auth service)
            if (authToken == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            Path imagePath = Paths.get(uploadDir).resolve(filename);
            File file = imagePath.toFile();

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

    // Image validation method
    private void validateImageFile(MultipartFile imageFile) {
        // Check file size (5MB limit)
        if (imageFile.getSize() > 5 * 1024 * 1024) {
            throw new IllegalArgumentException("Image file size must be less than 5MB");
        }

        // Check file type
        String contentType = imageFile.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("File must be an image");
        }

        // Check allowed image types
        Set<String> allowedTypes = Set.of("image/jpeg", "image/jpg", "image/png", "image/gif", "image/bmp");
        if (!allowedTypes.contains(contentType.toLowerCase())) {
            throw new IllegalArgumentException("Only JPEG, PNG, GIF, and BMP images are allowed");
        }

        // Check filename
        String filename = imageFile.getOriginalFilename();
        if (filename == null || filename.trim().isEmpty()) {
            throw new IllegalArgumentException("Image filename cannot be empty");
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
            case "bmp":
                return "image/bmp";
            case "webp":
                return "image/webp";
            default:
                return "application/octet-stream";
        }
    }
}