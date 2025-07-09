package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class SupplierRawMaterialRequestDTO {

    private Long inventoryId;
    private Long supplierId;
    private LocalDateTime date;
    private LocalDate expDate;
    private Integer quantity;
    private Integer currentQuantity;
    private Double unitPrice;
    private Long warehouseManagerId;
}
