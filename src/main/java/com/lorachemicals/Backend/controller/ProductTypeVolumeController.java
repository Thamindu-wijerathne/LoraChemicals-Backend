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
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/product-type-volumes")
public class ProductTypeVolumeController {

    private final ProductTypeVolumeService service;
    private static final Logger logger = LoggerFactory.getLogger(ProductTypeVolumeController.class);

    @Value("${image.upload.dir:uploads/images}")
    private String uploadDir;

    public ProductTypeVolumeController(ProductTypeVolumeService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        try {
            logger.info("GET /product-type-volumes/all called");
            List<ProductTypeVolumeResponseDTO> all = service.getAll();
            return ResponseEntity.ok(all);
        } catch (Exception e) {
            logger.error("Error fetching product list: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching products");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        ProductTypeVolumeResponseDTO dto = service.getById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> create(
            @RequestPart("data") ProductTypeVolumeRequestDTO dto,
            @RequestPart(value = "image", required = false) MultipartFile imageFile
    ) {
        try {
            String imagePath = null;
            if (imageFile != null && !imageFile.isEmpty()) {
                imagePath = saveImageFile(imageFile);
            }
            ProductTypeVolumeResponseDTO created = service.create(dto, imagePath);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            logger.error("Error creating ProductTypeVolume", e);
            return ResponseEntity.internalServerError().body("Failed to create product");
        }
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestPart("data") ProductTypeVolumeRequestDTO dto,
            @RequestPart(value = "image", required = false) MultipartFile imageFile
    ) {
        try {
            String imagePath = null;
            if (imageFile != null && !imageFile.isEmpty()) {
                imagePath = saveImageFile(imageFile);
            }
            ProductTypeVolumeResponseDTO updated = service.update(id, dto, imagePath);
            return updated != null ? ResponseEntity.ok(updated)
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        } catch (Exception e) {
            logger.error("Error updating ProductTypeVolume", e);
            return ResponseEntity.internalServerError().body("Failed to update product");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean deleted = service.delete(id);
        return deleted ? ResponseEntity.ok("Deleted successfully")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
    }

    // Optional: Serve uploaded images
    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> serveImage(@PathVariable String filename) {
        try {
            File file = Paths.get(uploadDir, filename).toFile();
            if (!file.exists()) return ResponseEntity.notFound().build();

            Resource resource = new FileSystemResource(file);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // or determine type dynamically
                    .body(resource);
        } catch (Exception e) {
            logger.error("Error serving image {}", filename, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private String saveImageFile(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + StringUtils.cleanPath(file.getOriginalFilename());
        File uploadPath = new File(uploadDir);
        if (!uploadPath.exists()) uploadPath.mkdirs();
        File dest = new File(uploadPath, fileName);
        file.transferTo(dest);
        return uploadDir + "/" + fileName;
    }
}
