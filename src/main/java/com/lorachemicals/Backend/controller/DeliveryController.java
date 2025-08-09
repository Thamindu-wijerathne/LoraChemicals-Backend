package com.lorachemicals.Backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lorachemicals.Backend.dto.DeliveryRequestDTO;
import com.lorachemicals.Backend.dto.DeliveryResponseDTO;
import com.lorachemicals.Backend.services.DeliveryService;
import com.lorachemicals.Backend.util.AccessControlUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @PostMapping
    public ResponseEntity<?> createDelivery(@RequestBody DeliveryRequestDTO requestDTO, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse", "admin");
        try {    
            DeliveryResponseDTO responseDTO = deliveryService.createDelivery(requestDTO);
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("❌ Error creating delivery: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<DeliveryResponseDTO>> getAllDeliveries() {
        List<DeliveryResponseDTO> deliveries = deliveryService.getAllDeliveries();
        return new ResponseEntity<>(deliveries, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryResponseDTO> getDeliveryById(@PathVariable Long id) {
        Optional<DeliveryResponseDTO> delivery = deliveryService.getDeliveryById(id);
        return delivery.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                      .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/salesrep/{srepid}")
    public ResponseEntity<List<DeliveryResponseDTO>> getDeliveriesBySalesRep(@PathVariable Long srepid) {
        List<DeliveryResponseDTO> deliveries = deliveryService.getDeliveriesBySalesRep(srepid);
        return new ResponseEntity<>(deliveries, HttpStatus.OK);
    }

    @GetMapping("/route/{routeid}")
    public ResponseEntity<List<DeliveryResponseDTO>> getDeliveriesByRoute(@PathVariable Long routeid) {
        List<DeliveryResponseDTO> deliveries = deliveryService.getDeliveriesByRoute(routeid);
        return new ResponseEntity<>(deliveries, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<DeliveryResponseDTO>> getDeliveriesByStatus(@PathVariable int status) {
        List<DeliveryResponseDTO> deliveries = deliveryService.getDeliveriesByStatus(status);
        return new ResponseEntity<>(deliveries, HttpStatus.OK);
    }


}
