package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class SupplierRawMaterialResponseDTO {

    private Long inventoryId;
    // Replace rawMaterialName with a clearer identifier field, since name doesn't exist
    private Long rawMaterialInventoryId;

    private Long supplierId;
    private String supplierName;

    private LocalDate date;
    private LocalDate expDate;
    private Integer quantity;

    private Long warehouseManagerId;
    private String warehouseManagerName;

    private Double unitPrice;
    private Double totalPrice;

}
