package com.lorachemicals.Backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lorachemicals.Backend.dto.DeductExtrasRequestDTO;
import com.lorachemicals.Backend.dto.DeliveryRequestDTO;
import com.lorachemicals.Backend.dto.DeliveryResponseDTO;
import com.lorachemicals.Backend.dto.SalesRepDeliveryResponseDTO;
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


    // view on going delivery details by spesific salesrep
    @GetMapping("/salesrep/{srepid}/detailed")
    public ResponseEntity<SalesRepDeliveryResponseDTO> getDetailedDeliveryBySalesRep(@PathVariable Long srepid, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "salesrep", "admin", "warehouse");
        try {
            SalesRepDeliveryResponseDTO delivery = deliveryService.getDetailedDeliveryBySalesRep(srepid);
            if (delivery != null) {
                return new ResponseEntity<>(delivery, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            System.err.println("❌ Error getting detailed delivery for sales rep " + srepid + ": " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateDeliveryStatus(@PathVariable Long id, @RequestBody StatusUpdateRequest statusRequest, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse", "admin", "salesrep");
        try {
            boolean updated = deliveryService.updateDeliveryStatus(id, statusRequest.getStatus());
            if (updated) {
                return ResponseEntity.ok().body("Delivery status updated successfully");
            } else {
                return ResponseEntity.badRequest().body("Failed to update delivery status");
            }
        } catch (RuntimeException e) {
            System.err.println("❌ Runtime error updating delivery status: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Unexpected error updating delivery status: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/deduct-extras")
    public ResponseEntity<?> deductExtrasFromVehicle(@RequestBody DeductExtrasRequestDTO requestDTO, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "salesrep", "admin");
        try {
            String result = deliveryService.deductExtrasFromVehicle(requestDTO);
            return ResponseEntity.ok().body(result);
        } catch (RuntimeException e) {
            System.err.println("❌ Runtime error deducting extras: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Unexpected error deducting extras: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    // Get orders by delivery ID
    @GetMapping("/{deliveryId}/orders")
    public ResponseEntity<?> getOrdersByDeliveryId(@PathVariable Long deliveryId, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse", "admin", "salesrep");
        try {
            List<SalesRepDeliveryResponseDTO.DeliveryOrderDetail> orders = deliveryService.getOrdersByDeliveryId(deliveryId);
            return ResponseEntity.ok().body(orders);
        } catch (RuntimeException e) {
            System.err.println("❌ Runtime error getting orders for delivery " + deliveryId + ": " + e.getMessage());
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Unexpected error getting orders for delivery " + deliveryId + ": " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    // Inner class for status update request
    public static class StatusUpdateRequest {
        private int status;
        
        public int getStatus() {
            return status;
        }
        
        public void setStatus(int status) {
            this.status = status;
        }
    }


}
