package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.model.Customer;
import com.lorachemicals.Backend.model.SalesRep;
import com.lorachemicals.Backend.model.WarehouseManager;
import com.lorachemicals.Backend.services.CustomerService;
import com.lorachemicals.Backend.services.SalesrepService;
import com.lorachemicals.Backend.services.WarehouseManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/getid")
public class StoreIDforstorageController {

    private final CustomerService customerService;
    private final SalesrepService salesrepService;
    private final WarehouseManagerService warehouseManagerService;

    public StoreIDforstorageController(CustomerService customerService, SalesrepService salesrepService, WarehouseManagerService warehouseManagerService) {
        this.customerService = customerService;
        this.salesrepService = salesrepService;
        this.warehouseManagerService = warehouseManagerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleEntityByUserId(@PathVariable Long id) {
        Customer customer = customerService.getCustomerByUserId(id);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        }

        SalesRep salesRep = salesrepService.getSalesRepById(id);
        if (salesRep != null) {
            return ResponseEntity.ok(salesRep);
        }

        WarehouseManager warehouseManager = warehouseManagerService.getWarehouseManagerById(id);
        if (warehouseManager != null) {
            return ResponseEntity.ok(warehouseManager);
        }

        return ResponseEntity.status(404).body("No matching role found for user ID: " + id);
    }
}
