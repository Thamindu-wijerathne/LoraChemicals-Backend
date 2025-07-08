package com.lorachemicals.Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseWithInventoryUpdateDTO {
    private Long supplierId;
    private Long inventoryId;
    private Long warehouseManagerId;
    private LocalDate date;
    private LocalDate expDate;
    private Integer quantity;
    private double unitPrice;

}
