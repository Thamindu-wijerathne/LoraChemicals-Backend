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

import java.util.List;

@RestController
@RequestMapping("/customer-order")
public class CustomerOrderController {

    private final CustomerOrderService customerOrderService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public CustomerOrderController(CustomerOrderService customerOrderService) {
        this.customerOrderService = customerOrderService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> createOrder(@RequestBody CustomerOrderRequestDTO data, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "customer"); // Adjust roles as needed
//      return ResponseEntity.internalServerError().body("Order creation failed: ");

        try {
            CustomerOrder created = customerOrderService.createOrder(data);
            logger.info("order items : {}", data);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            logger.error("Order creation failed", e);
            return ResponseEntity.internalServerError().body("Order creation failed: " + e.getMessage());
        }
    }

    @GetMapping("/get-customer-all-orders/{id}")
    public ResponseEntity<?> getAllOrders(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "customer");

        try {
            List<CustomerOrderResponseDTO> orders = customerOrderService.getOrdersByCustomerId(id);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            logger.error("Order detail get failed", e);
            return ResponseEntity.internalServerError().body("Order detail get failed: " + e.getMessage());
        }
    }
}
