package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BottleRequestDTO {
    private Long bottleId; // to link the BottleType
    private int quantity;
    private String location;

    public BottleRequestDTO() {}

    public BottleRequestDTO(Long bottleId, int quantity) {
        this.bottleId = bottleId;
        this.quantity = quantity;
    }
}

