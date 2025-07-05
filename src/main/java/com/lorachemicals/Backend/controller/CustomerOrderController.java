package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.CustomerOrderRequestDTO;
import com.lorachemicals.Backend.dto.CustomerOrderResponseDTO;
import com.lorachemicals.Backend.model.CustomerOrder;
import com.lorachemicals.Backend.services.CustomerOrderService;
import com.lorachemicals.Backend.util.AccessControlUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer-order")
public class CustomerOrderController {

    private final CustomerOrderService cusotmerOrderService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public CustomerOrderController(CustomerOrderService customerOrderService) {
        this.cusotmerOrderService = customerOrderService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> createOrder(@RequestBody CustomerOrderRequestDTO data, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "customer"); // Adjust roles as needed
        try {
            CustomerOrder created = cusotmerOrderService.createOrder(data);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            logger.error("Order creation failed", e);
            return ResponseEntity.internalServerError().body("Order creation failed: " + e.getMessage());
        }
    }
}
