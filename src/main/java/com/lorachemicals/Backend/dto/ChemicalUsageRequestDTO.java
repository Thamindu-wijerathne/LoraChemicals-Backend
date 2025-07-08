package com.lorachemicals.Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChemicalUsageRequestDTO {
    private Long prodid;
    private Long inventoryid;
    private Double quantity;

    public Long getProdId() {
        return prodid;
    }

    public Long getInventoryId() {
        return inventoryid;
    }
}
