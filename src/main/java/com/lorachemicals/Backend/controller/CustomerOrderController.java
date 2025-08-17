package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.CustomerOrderRequestDTO;
import com.lorachemicals.Backend.dto.CustomerOrderResponseDTO;
import com.lorachemicals.Backend.dto.TrendingProductsDTO;
import com.lorachemicals.Backend.model.CustomerOrder;
import com.lorachemicals.Backend.model.CustomerOrderItem;
import com.lorachemicals.Backend.services.CustomerOrderService;
import com.lorachemicals.Backend.util.AccessControlUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
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
    public ResponseEntity<?> getAllOrdersOfACustomer(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "customer");

        try {
            List<CustomerOrderResponseDTO> orders = customerOrderService.getOrdersByCustomerId(id);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            logger.error("Order detail get failed", e);
            return ResponseEntity.internalServerError().body("Order detail get failed: " + e.getMessage());
        }
    }
    
    @GetMapping("/get-all-orders")
    public ResponseEntity<?> getAllOrders(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            List<CustomerOrderResponseDTO> orders = customerOrderService.getOrders();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            logger.error("Order detail get failed", e);
            return ResponseEntity.internalServerError().body("Order detail get failed: " + e.getMessage());
        }
    }

    @PutMapping("/accept/{id}")
    public ResponseEntity<?> acceptOrderByWarehouse(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            customerOrderService.acceptOrder(id);
            return ResponseEntity.ok("Order accepted successfully");
        } catch (Exception e) {
            logger.error("Order accepted  failed", e);
            return ResponseEntity.internalServerError().body("Order accepted failed: " + e.getMessage());
        }
    }

    @GetMapping("/trending-products")
    public ResponseEntity<?> getTrendingProducts(HttpServletRequest request) {
        try {
            List<TrendingProductsDTO> trending = customerOrderService.getTrendingProducts();
            return ResponseEntity.ok(trending);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to fetch trending products: " + e.getMessage());
        }
    }

    @PutMapping("/complete-order/{id}")
    public ResponseEntity<?> completeOrder(@PathVariable Long id, @RequestBody CustomerOrderRequestDTO requestDTO, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "salesrep");
        System.err.println("comple order runned");

        try {
            customerOrderService.completeOrder(id, requestDTO);
            return ResponseEntity.ok("Order Completed successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to complete order: " + e.getMessage());
        }
    }



}
