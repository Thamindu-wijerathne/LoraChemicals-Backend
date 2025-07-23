package com.lorachemicals.Backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeliveryInventoryResponseDTO {

    private Long deliveryId;
    private Long inventoryId;
    private LocalDateTime deliveryDate;

    private int quantity;
    private int currentQuantity;

    private String inventoryName;
    private String deliveryStatus;  // optional, from Delivery entity
    private String warehouseManagerName;

    public DeliveryInventoryResponseDTO() {}

    public DeliveryInventoryResponseDTO(Long deliveryId, Long inventoryId, LocalDateTime deliveryDate,
                                        int quantity, int currentQuantity,
                                        String inventoryName, String deliveryStatus, String warehouseManagerName) {
        this.deliveryId = deliveryId;
        this.inventoryId = inventoryId;
        this.deliveryDate = deliveryDate;
        this.quantity = quantity;
        this.currentQuantity = currentQuantity;
        this.inventoryName = inventoryName;
        this.deliveryStatus = deliveryStatus;
        this.warehouseManagerName = warehouseManagerName;
    }

    // Getters and Setters (or use Lombok @Getter/@Setter if allowed)
}
