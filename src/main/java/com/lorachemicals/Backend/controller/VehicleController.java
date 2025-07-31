package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.VehicleRequestDTO;
import com.lorachemicals.Backend.dto.VehicleResponseDTO;
import com.lorachemicals.Backend.services.VehicleService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);

    @Value("${image.upload.dir:uploads/vehicles}")
    private String uploadDir;

    @PostMapping("/add")
    public ResponseEntity<?> createVehicle(
            HttpServletRequest request,
            @RequestPart("vehicle") VehicleRequestDTO requestDTO,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
        logger.info("POST /Create Vehicle Api Called {}", requestDTO);
        try {
            logger.info("POST /Create Vehicle Api Called");
            AccessControlUtil.checkAccess(request, "admin");

            // Validate image file if provided
            if (imageFile != null && !imageFile.isEmpty()) {
                validateImageFile(imageFile);
            }

            VehicleResponseDTO responseDTO = vehicleService.createVehicle(requestDTO, imageFile, uploadDir);
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input: {}", e.getMessage());
            return new ResponseEntity<>("Invalid input: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            logger.error("Error saving image: {}", e.getMessage(), e);
            return new ResponseEntity<>("Error saving image: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RuntimeException e) {
            logger.error("Runtime error: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Internal error: {}", e.getMessage(), e);
            return new ResponseEntity<>("Internal server error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<?> updateVehicleStatus(@PathVariable Long id, HttpServletRequest request, @RequestBody VehicleRequestDTO vehicleRequestDTO) {
        AccessControlUtil.checkAccess(request, "admin");
        logger.info("PUT /Update Vehicle status Api Called {}", vehicleRequestDTO);
        try {
            logger.info("PUT /Update Vehicle status Api Called");
            vehicleService.updateVehicleStatus(id, vehicleRequestDTO.getStatus());
            return ResponseEntity.ok("Vehicle status updated successfully for id: " + id);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle cannot update with id: " + id);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateVehicle(
            @PathVariable Long id,
            HttpServletRequest request,
            @RequestPart("vehicle") VehicleRequestDTO requestDTO,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
        try {
            logger.info("PUT /Update Vehicle Api Called");
            AccessControlUtil.checkAccess(request, "admin");

            // Validate image file if provided
            if (imageFile != null && !imageFile.isEmpty()) {
                validateImageFile(imageFile);
            }

            VehicleResponseDTO responseDTO = vehicleService.updateVehicle(id, requestDTO, imageFile, uploadDir);
            if (responseDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle not found with id: " + id);
            }
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input: {}", e.getMessage());
            return new ResponseEntity<>("Invalid input: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            logger.error("Error saving image: {}", e.getMessage(), e);
            return new ResponseEntity<>("Error saving image: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RuntimeException e) {
            logger.error("Runtime error: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Internal error: {}", e.getMessage(), e);
            return new ResponseEntity<>("Internal server error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Keep existing GET and DELETE methods as they are
    @GetMapping
    public ResponseEntity<?> getAllVehicles(HttpServletRequest request) {
        try {
            logger.info("GET /Get All Vehicles Api Called");
            AccessControlUtil.checkAccess(request, "admin");
            List<VehicleResponseDTO> vehicles = vehicleService.getAllVehicles();
            logger.info("GET vehicle data : {}", vehicles);
            return new ResponseEntity<>(vehicles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVehicleById(@PathVariable Long id, HttpServletRequest request) {
        try {
            logger.info("GET /Get One Vehicle Api Called");
            AccessControlUtil.checkAccess(request, "admin");
            VehicleResponseDTO responseDTO = vehicleService.getVehicleById(id);
            return responseDTO != null ? ResponseEntity.ok(responseDTO)
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle not found with id: " + id);
        } catch (RuntimeException e) {
            logger.error("Runtime error: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error fetching vehicle with id {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching vehicle: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable Long id, HttpServletRequest request) {
        try {
            logger.info("DELETE /Delete Vehicle Api Called");
            AccessControlUtil.checkAccess(request, "admin");
            boolean deleted = vehicleService.deleteVehicle(id);
            if (deleted) {
                return ResponseEntity.ok().body("Vehicle deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle not found with id: " + id);
            }
        } catch (RuntimeException e) {
            logger.error("Runtime error: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error deleting vehicle: {}", e.getMessage(), e);
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
                logger.warn("Vehicle image file not found: {}", filename);
                return ResponseEntity.notFound().build();
            }

            Resource resource = new FileSystemResource(file);
            String contentType = determineContentType(filename);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .body(resource);
        } catch (Exception e) {
            logger.error("Error serving vehicle image {}: {}", filename, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Image validation method
    private void validateImageFile(MultipartFile imageFile) {
        // Check file size (10MB limit for vehicle images)
        if (imageFile.getSize() > 10 * 1024 * 1024) {
            throw new IllegalArgumentException("Vehicle image file size must be less than 10MB");
        }

        // Check file type
        String contentType = imageFile.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("File must be an image");
        }

        // Check allowed image types
        Set<String> allowedTypes = Set.of("image/jpeg", "image/jpg", "image/png", "image/gif", "image/bmp", "image/webp");
        if (!allowedTypes.contains(contentType.toLowerCase())) {
            throw new IllegalArgumentException("Only JPEG, PNG, GIF, BMP, and WebP images are allowed");
        }

        // Check filename
        String filename = imageFile.getOriginalFilename();
        if (filename == null || filename.trim().isEmpty()) {
            throw new IllegalArgumentException("Vehicle image filename cannot be empty");
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
