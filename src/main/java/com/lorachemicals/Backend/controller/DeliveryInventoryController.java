package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.model.DeliveryInventory;
import com.lorachemicals.Backend.model.DeliveryInventoryId;
import com.lorachemicals.Backend.services.DeliveryInventoryService;
import com.lorachemicals.Backend.util.AccessControlUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/delivery-inventory")
public class DeliveryInventoryController {

    @Autowired
    private DeliveryInventoryService deliveryInventoryService;

    // GET /delivery-inventory/all
    @GetMapping("/all")
    public List<DeliveryInventory> getAll(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin", "warehouse");
        try {
            return deliveryInventoryService.getAllDeliveryInventories();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch delivery inventory data.", e);
        }
    }

    // GET /delivery-inventory/{deliveryid}
    @GetMapping("/{deliveryid}")
    public List<DeliveryInventory> getByDeliveryId(@PathVariable Long deliveryid, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            return deliveryInventoryService.getByDeliveryId(deliveryid);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch data for delivery ID: " + deliveryid, e);
        }
    }

    // Get DeliveryInventory by composite key
    @GetMapping("/item")
    public DeliveryInventory getById(
            @RequestParam Long deliveryid,
            @RequestParam Long inventoryid,
            // assuming deliverydate is passed as ISO string like "2025-07-23T10:15:30"
            @RequestParam String deliverydate,
            HttpServletRequest request) {

        AccessControlUtil.checkAccess(request, "warehouse", "admin");

        try {
            // Parse deliverydate string to LocalDateTime
            java.time.LocalDateTime dt = java.time.LocalDateTime.parse(deliverydate);

            DeliveryInventoryId id = new DeliveryInventoryId(deliveryid, inventoryid, dt);

            DeliveryInventory deliveryInventory = deliveryInventoryService.getById(id);

            if (deliveryInventory == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "DeliveryInventory not found for given ID");
            }

            return deliveryInventory;
        } catch (java.time.format.DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format for deliverydate. Use ISO format: yyyy-MM-ddTHH:mm:ss", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching DeliveryInventory", e);
        }
    }
}
