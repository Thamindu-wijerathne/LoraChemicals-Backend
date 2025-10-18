package com.lorachemicals.Backend.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lorachemicals.Backend.dto.CustomerBillResponseDTO;
import com.lorachemicals.Backend.model.CustomerBill;
import com.lorachemicals.Backend.services.CustomerBillService;
import com.lorachemicals.Backend.util.AccessControlUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/customer-bills")
public class CustomerBillController {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CustomerBillController.class);

    @Autowired
    private CustomerBillService customerBillService;

    /**
     * Get all customer bills for a specific delivery
     * This shows all orders/bills created during a particular delivery
     */
    @GetMapping("/delivery/{deliveryId}")
    public ResponseEntity<?> getCustomerBillsByDeliveryId(@PathVariable Long deliveryId, HttpServletRequest request) {
        try {
            // Check access permissions
            AccessControlUtil.checkAccess(request, "salesrep", "admin", "warehouse");
            
            logger.info("Fetching customer bills for delivery ID: {}", deliveryId);
            
            List<CustomerBill> customerBills = customerBillService.getCustomerBillsByDeliveryId(deliveryId);
            
            if (customerBills.isEmpty()) {
                logger.info("No customer bills found for delivery ID: {}", deliveryId);
                return ResponseEntity.ok(List.of()); // Return empty list instead of 404
            }
            
            // Convert to DTOs
            List<CustomerBillResponseDTO> response = customerBills.stream()
                    .map(customerBillService::convertToResponseDTO)
                    .collect(Collectors.toList());
            
            logger.info("Found {} customer bills for delivery ID: {}", response.size(), deliveryId);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error fetching customer bills for delivery ID: {}", deliveryId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching customer bills: " + e.getMessage());
        }
    }

    /**
     * Get customer bill by bill ID
     */
    @GetMapping("/bill/{billId}")
    public ResponseEntity<?> getCustomerBillByBillId(@PathVariable Long billId, HttpServletRequest request) {
        try {
            AccessControlUtil.checkAccess(request, "salesrep", "admin", "warehouse");
            
            logger.info("Fetching customer bill for bill ID: {}", billId);
            
            CustomerBill customerBill = customerBillService.getCustomerBillByBillId(billId);
            
            if (customerBill == null) {
                logger.warn("Customer bill not found for bill ID: {}", billId);
                return ResponseEntity.notFound().build();
            }
            
            CustomerBillResponseDTO response = customerBillService.convertToResponseDTO(customerBill);
            
            logger.info("Found customer bill for bill ID: {}", billId);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error fetching customer bill for bill ID: {}", billId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching customer bill: " + e.getMessage());
        }
    }

    /**
     * Get all customer bills by sales rep ID
     * Shows all bills created by a specific sales representative
     */
    @GetMapping("/salesrep/{srepId}")
    public ResponseEntity<?> getCustomerBillsBySalesRepId(@PathVariable Long srepId, HttpServletRequest request) {
        try {
            AccessControlUtil.checkAccess(request, "salesrep", "admin", "warehouse");
            
            logger.info("Fetching customer bills for sales rep ID: {}", srepId);
            
            List<CustomerBill> customerBills = customerBillService.getCustomerBillsBySalesRepId(srepId);
            
            if (customerBills.isEmpty()) {
                logger.info("No customer bills found for sales rep ID: {}", srepId);
                return ResponseEntity.ok(List.of());
            }
            
            List<CustomerBillResponseDTO> response = customerBills.stream()
                    .map(customerBillService::convertToResponseDTO)
                    .collect(Collectors.toList());
            
            logger.info("Found {} customer bills for sales rep ID: {}", response.size(), srepId);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error fetching customer bills for sales rep ID: {}", srepId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching customer bills: " + e.getMessage());
        }
    }
}