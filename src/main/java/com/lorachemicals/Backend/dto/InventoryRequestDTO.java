package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InventoryRequestDTO {
    private String location;

    public InventoryRequestDTO(){}

    public InventoryRequestDTO(String location) {
        this.location = location;
    }
}
