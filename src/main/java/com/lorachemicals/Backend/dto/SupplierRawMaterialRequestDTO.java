package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class SupplierRawMaterialRequestDTO {

    private Long inventoryId;
    private Long supplierId;
    private LocalDate date;
    private LocalDate expDate;
    private Integer quantity;
    private Double unitPrice;
    private Long warehouseManagerId;

}
