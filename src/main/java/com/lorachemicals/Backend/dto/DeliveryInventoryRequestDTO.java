package com.lorachemicals.Backend.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class DeliveryInventoryRequestDTO {

    private Long deliveryId;
    private Long inventoryId;
    private LocalDateTime deliveryDate;

    private int quantity;
    private int currentQuantity;

    private Long warehouseManagerId;

    public DeliveryInventoryRequestDTO() {}

    public DeliveryInventoryRequestDTO(Long deliveryId, Long inventoryId, LocalDateTime deliveryDate,
                                       int quantity, int currentQuantity, Long warehouseManagerId) {
        this.deliveryId = deliveryId;
        this.inventoryId = inventoryId;
        this.deliveryDate = deliveryDate;
        this.quantity = quantity;
        this.currentQuantity = currentQuantity;
        this.warehouseManagerId = warehouseManagerId;
    }

    // Getters and Setters (or use Lombok if you prefer)
}