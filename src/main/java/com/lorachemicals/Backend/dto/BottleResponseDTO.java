package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BottleResponseDTO {

    private Long inventoryId;
    private Long bottleId;
    private int quantity;

    public BottleResponseDTO() {}

    public BottleResponseDTO(Long inventoryId, Long bottleId, int quantity) {
        this.inventoryId = inventoryId;
        this.bottleId = bottleId;
        this.quantity = quantity;
    }
}
