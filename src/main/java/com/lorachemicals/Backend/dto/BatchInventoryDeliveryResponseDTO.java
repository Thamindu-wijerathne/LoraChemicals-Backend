package com.lorachemicals.Backend.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BatchInventoryDeliveryResponseDTO {
    private Long batchtypeid;
    private String batchTypeName;
    private Long deliveryid;
    private LocalDateTime datetime;
    private String type;
    private Integer quantity;
    private Integer currentQuantity;
    private Long wmid;
    private String warehouseManagerName;
}
