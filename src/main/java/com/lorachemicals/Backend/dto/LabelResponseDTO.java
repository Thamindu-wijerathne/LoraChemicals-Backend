package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LabelResponseDTO {
    private Long inventoryId;
    private Long labelId;
    private int quantity;

    public LabelResponseDTO() {}

    public LabelResponseDTO(Long inventoryId, Long labelId, int quantity) {
        this.inventoryId = inventoryId;
        this.labelId = labelId;
        this.quantity = quantity;
    }
}
