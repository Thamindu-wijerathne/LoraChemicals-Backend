package com.lorachemicals.Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseWithInventoryUpdateDTO {
    private Long supplierId;
    private Long inventoryId;
    private Long warehouseManagerId;
    private LocalDateTime date;
    private LocalDate expDate;
    private Integer quantity;
    private double unitPrice;

}
