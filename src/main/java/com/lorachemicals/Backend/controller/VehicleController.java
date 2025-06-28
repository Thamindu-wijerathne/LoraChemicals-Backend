package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.VehicleRequestDTO;
import com.lorachemicals.Backend.dto.VehicleResponseDTO;
import com.lorachemicals.Backend.services.VehicleService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;
    private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);

    @PostMapping
    public ResponseEntity<?> createVehicle(@RequestBody VehicleRequestDTO requestDTO, HttpServletRequest request) {
        try {
            logger.info("POST /Create Vehicle Api Called");
            AccessControlUtil.checkAccess(request, "admin");
            VehicleResponseDTO responseDTO = vehicleService.createVehicle(requestDTO);
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVehicle(@PathVariable Long id, @RequestBody VehicleRequestDTO requestDTO, HttpServletRequest request) {
        try {
            logger.info("PUT /Update Vehicle Api Called");
            AccessControlUtil.checkAccess(request, "admin");
            VehicleResponseDTO responseDTO = vehicleService.updateVehicle(id, requestDTO);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllVehicles(HttpServletRequest request) {
        try {
            logger.info("GET /Get All Vehicles Api Called");
            AccessControlUtil.checkAccess(request, "admin");
            List<VehicleResponseDTO> vehicles = vehicleService.getAllVehicles();
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
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable Long id, HttpServletRequest request) {
        try {
            logger.info("DELETE /Delete Vehicle Api Called");
            AccessControlUtil.checkAccess(request, "admin");
            vehicleService.deleteVehicle(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}