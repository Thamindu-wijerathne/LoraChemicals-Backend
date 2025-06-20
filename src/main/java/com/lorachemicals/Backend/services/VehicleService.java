package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.VehicleRequestDTO;
import com.lorachemicals.Backend.dto.VehicleResponseDTO;
import com.lorachemicals.Backend.model.Vehicle;
import com.lorachemicals.Backend.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public VehicleResponseDTO createVehicle(VehicleRequestDTO requestDTO) {
        try {
            Vehicle vehicle = new Vehicle(requestDTO.getVehicleNo(),requestDTO.getVehicleType(),requestDTO.getCapacity()); // Use default constructor
//            vehicle.setVehicleNo(requestDTO.getVehicleNo());
//            vehicle.setVehicleType(requestDTO.getVehicleType());
//            vehicle.setCapacity(requestDTO.getCapacity());
            Vehicle savedVehicle = vehicleRepository.save(vehicle);
            return convertToResponseDTO(savedVehicle);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create vehicle: " + e.getMessage());
        }
    }

    public VehicleResponseDTO updateVehicle(Long id, VehicleRequestDTO requestDTO) {
        try {
            Vehicle vehicle = vehicleRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));

            vehicle.setVehicleNo(requestDTO.getVehicleNo());
            vehicle.setVehicleType(requestDTO.getVehicleType());
            vehicle.setCapacity(requestDTO.getCapacity());

            Vehicle updatedVehicle = vehicleRepository.save(vehicle);
            return convertToResponseDTO(updatedVehicle);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
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
            throw new RuntimeException("Failed to retrieve vehicle: " + e.getMessage());
        }
    }

    public void deleteVehicle(Long id) {
        try {
            if (!vehicleRepository.existsById(id)) {
                throw new RuntimeException("Vehicle not found with id: " + id);
            }
            vehicleRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete vehicle: " + e.getMessage());
        }
    }

    private VehicleResponseDTO convertToResponseDTO(Vehicle vehicle) {
        VehicleResponseDTO dto = new VehicleResponseDTO();
        dto.setId(vehicle.getId());
        dto.setVehicleNo(vehicle.getVehicleNo());
        dto.setVehicleType(vehicle.getVehicleType());
        dto.setCapacity(vehicle.getCapacity());
        return dto;
    }

}