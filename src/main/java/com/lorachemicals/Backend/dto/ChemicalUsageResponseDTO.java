package com.lorachemicals.Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChemicalUsageResponseDTO {
    private Long inventoryid;
    private Long prodid;
    private Double quantity;
}
