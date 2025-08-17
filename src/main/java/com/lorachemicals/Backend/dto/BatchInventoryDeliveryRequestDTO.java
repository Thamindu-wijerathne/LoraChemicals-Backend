package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BatchInventoryDeliveryRequestDTO {
    private Long batchtypeid;
    private LocalDateTime datetime;
    private String type;
    private Integer quantity;
    private Integer currentQuantity;
    private Long wmid; // WarehouseManager ID
}
