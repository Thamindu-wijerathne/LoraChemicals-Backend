package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.VehicleRequestDTO;
import com.lorachemicals.Backend.dto.VehicleResponseDTO;
import com.lorachemicals.Backend.model.Vehicle;
import com.lorachemicals.Backend.repository.VehicleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class VehicleService {

    private static final Logger logger = LoggerFactory.getLogger(VehicleService.class);

    @Autowired
    private VehicleRepository vehicleRepository;

    @Value("${server.base-url:http://localhost:8080}")
    private String baseUrl;

    public VehicleResponseDTO createVehicle(VehicleRequestDTO requestDTO, MultipartFile imageFile, String uploadDir) throws IOException {
        try {
            Vehicle vehicle = new Vehicle(
                    requestDTO.getVehicleNo(),
                    requestDTO.getVehicleType(),
                    requestDTO.getCapacity(),
                    requestDTO.getDescription(),
                    null, // Will set image after processing
                    requestDTO.getSeats(),
                    requestDTO.getDate(),
                    "active"     //this is vehicle status saving as 1 when create vehicle
            );

            // Handle image if provided
            if (imageFile != null && !imageFile.isEmpty()) {
                String imagePath = saveImageFile(imageFile, uploadDir, requestDTO.getVehicleNo());
                vehicle.setImage(imagePath);
            }

            Vehicle savedVehicle = vehicleRepository.save(vehicle);
            return convertToResponseDTO(savedVehicle);
        } catch (IOException e) {
            logger.error("Error saving vehicle image: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Failed to create vehicle: {}", e.getMessage());
            throw new RuntimeException("Failed to create vehicle: " + e.getMessage());
        }
    }

    public VehicleResponseDTO updateVehicle(Long id, VehicleRequestDTO requestDTO, MultipartFile imageFile, String uploadDir) throws IOException {
        try {
            Vehicle vehicle = vehicleRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));

            // Update vehicle fields
            vehicle.setVehicleNo(requestDTO.getVehicleNo());
            vehicle.setVehicleType(requestDTO.getVehicleType());
            vehicle.setCapacity(requestDTO.getCapacity());
            vehicle.setDescription(requestDTO.getDescription());
            vehicle.setSeats(requestDTO.getSeats());
            vehicle.setDate(requestDTO.getDate());

            // Handle image update if provided
            if (imageFile != null && !imageFile.isEmpty()) {
                // Delete old image if exists
                if (vehicle.getImage() != null && !vehicle.getImage().isEmpty()) {
                    deleteImageFile(vehicle.getImage(), uploadDir);
                }

                String imagePath = saveImageFile(imageFile, uploadDir, requestDTO.getVehicleNo());
                vehicle.setImage(imagePath);
            }

            Vehicle updatedVehicle = vehicleRepository.save(vehicle);
            return convertToResponseDTO(updatedVehicle);
        } catch (IOException e) {
            logger.error("Error updating vehicle image: {}", e.getMessage());
            throw e;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Failed to update vehicle: {}", e.getMessage());
            throw new RuntimeException("Failed to update vehicle: " + e.getMessage());
        }
    }

    public List<VehicleResponseDTO> getAllVehicles() {
        try {
            return vehicleRepository.findAll()
                    .stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Failed to retrieve vehicles: {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve vehicles: " + e.getMessage());
        }
    }

    public VehicleResponseDTO getVehicleById(Long id) {
        try {
            Vehicle vehicle = vehicleRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));
            return convertToResponseDTO(vehicle);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Failed to retrieve vehicle: {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve vehicle: " + e.getMessage());
        }
    }

    public boolean deleteVehicle(Long id) {
        try {
            Optional<Vehicle> vehicleOpt = vehicleRepository.findById(id);
            if (vehicleOpt.isEmpty()) {
                return false;
            }

            Vehicle vehicle = vehicleOpt.get();

            // Delete associated image file if exists
            if (vehicle.getImage() != null && !vehicle.getImage().isEmpty()) {
                try {
                    deleteImageFile(vehicle.getImage(), "uploads/vehicles");
                } catch (IOException e) {
                    logger.error("Error deleting vehicle image file: {}", e.getMessage());
                    // Continue with entity deletion even if image deletion fails
                }
            }

            vehicleRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            logger.error("Failed to delete vehicle: {}", e.getMessage());
            throw new RuntimeException("Failed to delete vehicle: " + e.getMessage());
        }
    }

    private String saveImageFile(MultipartFile imageFile, String uploadDir, String vehicleNo) throws IOException {
        // Create upload directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
            logger.info("Created vehicle upload directory: {}", uploadDir);
        }

        // Generate unique filename
        String originalFilename = imageFile.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String sanitizedVehicleNo = vehicleNo.replaceAll("[^a-zA-Z0-9]", "_");
        String uniqueFilename = "vehicle_" + sanitizedVehicleNo + "_" + System.currentTimeMillis() + fileExtension;

        // Save file
        Path filePath = uploadPath.resolve(uniqueFilename);
        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        logger.info("Vehicle image saved successfully: {}", filePath.toString());
        return uniqueFilename; // Return only filename, not full path
    }

    private void deleteImageFile(String imageName, String uploadDir) throws IOException {
        if (imageName != null && !imageName.isEmpty()) {
            Path imagePath = Paths.get(uploadDir).resolve(imageName);
            Files.deleteIfExists(imagePath);
            logger.info("Deleted vehicle image file: {}", imagePath);
        }
    }

    public void updateVehicleStatus(Long id, String status) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));
        vehicle.setStatus(status);
        vehicleRepository.save(vehicle);
    }

    private VehicleResponseDTO convertToResponseDTO(Vehicle vehicle) {
        VehicleResponseDTO dto = new VehicleResponseDTO();
        dto.setId(vehicle.getId());
        dto.setVehicleNo(vehicle.getVehicleNo());
        dto.setVehicleType(vehicle.getVehicleType());
        dto.setCapacity(vehicle.getCapacity());
        dto.setDescription(vehicle.getDescription());
        dto.setSeats(vehicle.getSeats());
        dto.setDate(vehicle.getDate());
        dto.setStatus(vehicle.getStatus());

        // Set image URL if image exists
        String imageUrl = null;
        if (vehicle.getImage() != null && !vehicle.getImage().isEmpty()) {
            imageUrl = baseUrl + "/vehicles/images/" + vehicle.getImage();
        }
        dto.setImage(imageUrl);

        return dto;
    }


}
